package jsf;

import jpa.entities.Lancamento;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.session.LancamentoFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jpa.entities.Item;
import jpa.entities.Lancamentoitem;
import jpa.session.ItemFacade;
import jpa.session.LancamentoitemFacade;

@Named("lancamentoController")
@SessionScoped
public class LancamentoController implements Serializable {

    private Lancamento current;
    private DataModel items = null;
    
    @EJB
    private jpa.session.LancamentoFacade ejbFacade;
    
    @EJB
    private jpa.session.ItemFacade ejbItemFacade;
    
    @EJB
    private jpa.session.LancamentoitemFacade ejbLanctoItemFacade;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private List<Item> selectItens;
    private Item[] selectedItens;

    public LancamentoController() {
    }
    
    public List<Item> getSelectItens() {
        return selectItens;
    }

    public Item[] getSelectedItens() {
        return selectedItens;
    }

    public void setSelectedItens(Item[] selectedItens) {
        this.selectedItens = selectedItens;
    }
    
    public Lancamento getSelected() {
        if (current == null) {
            current = new Lancamento();
            selectedItemIndex = -1;
        }
        return current;
    }

    private LancamentoFacade getFacade() {
        return ejbFacade;
    }
    
    private ItemFacade getItemFacade() {
        return ejbItemFacade;
    }
    
    private LancamentoitemFacade getLanctoItemFacade() {
        return ejbLanctoItemFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Lancamento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Lancamento();
        selectedItemIndex = -1;
        setSelectItens(false);
        return "Create";
    }

    private void setSelectItens(boolean isEdit) {
        selectItens = getItemFacade().findAll();
        List<Item> ids = new ArrayList<>();
        List<Lancamentoitem> selectLanctoItens;
        
        if (isEdit) {
            // busca na tabela lanctoItem os itens relacionados e seta a lista selectedItens
            selectLanctoItens = getLanctoItemFacade().findAll();
            selectLanctoItens.stream().filter((lanctoItem) -> (lanctoItem.getOidLancamento().getOid().equals(current.getOid()))).forEach((lanctoItem) -> {
                ids.add(lanctoItem.getOidItem());
            });
            
            selectedItens = new Item[ids.size()];
            for (int i = 0; i<ids.size(); i++) {
                selectedItens[i] = ids.get(i);
            }
        }
    }
    
    public String create() {
        try {
            double total = 0.0;
            
            if (selectItens != null) {
                for (Item selectedItem : selectedItens) {
                    total += selectedItem.getValor().doubleValue();
                }
            }
            
            current.setVlTotal(new BigDecimal(total));
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("LancamentoCreated"));
            
            Lancamentoitem lanctoItem;
            
            if (selectItens != null) {
                for (Item selectedItem : selectedItens) {
                    lanctoItem = new Lancamentoitem();
                    lanctoItem.setOidItem(selectedItem);
                    lanctoItem.setOidLancamento(current);
                    getLanctoItemFacade().create(lanctoItem);
                }
                
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("LancamentoitemCreated"));
            }
            
            
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Lancamento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        setSelectItens(true);
        return "Edit";
    }

    public String update() {
        try {
            double total = 0.0;
            
            if (selectItens != null) {
                for (Item selectedItem : selectedItens) {
                    total += selectedItem.getValor().doubleValue();
                }
            }
            
            current.setVlTotal(new BigDecimal(total));
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("LancamentoUpdated"));
            
            Lancamentoitem lanctoItem;
            List<Lancamentoitem> lanctoItens = getLanctoItemFacade().findAll();
            List<Lancamentoitem> deleteList = new ArrayList<>();
            List<Item> insertList = new ArrayList<>();
            Boolean achou;

            if (selectItens != null) {
                 for (Lancamentoitem lanctoIt : lanctoItens) {
                    achou = false;

                    if (lanctoIt.getOidLancamento().getOid().equals(current.getOid())) {
                        for (Item selectedItem : selectedItens) {
                            if (selectedItem.getOid().equals(lanctoIt.getOidItem().getOid())) {
                                achou = true;
                                break;
                            }
                        }

                        if (!achou) {
                            deleteList.add(lanctoIt);
                        }
                    }
                }
            
                deleteList.stream().forEach((deleteItem) -> {
                    getLanctoItemFacade().remove(deleteItem);
                });

                lanctoItens = getLanctoItemFacade().findAll();
                
                for (Item selectedItem : selectedItens) {
                    achou = false;
                    
                    for (Lancamentoitem lanctoIt : lanctoItens) {
                        if (lanctoIt.getOidLancamento().getOid().equals(current.getOid())) {
                            if (selectedItem.getOid().equals(lanctoIt.getOidItem().getOid())) {
                                achou = true;
                                break;
                            }
                        }
                    }
                    
                    if (!achou) {
                        insertList.add(selectedItem);
                    }
                    
                }
                
                for (Item insert : insertList) {
                    lanctoItem = new Lancamentoitem();
                    lanctoItem.setOidItem(insert);
                    lanctoItem.setOidLancamento(current);
                    getLanctoItemFacade().create(lanctoItem);
                }
                
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("LancamentoitemCreated"));
            }
            
           
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Lancamento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("LancamentoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Lancamento getLancamento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Lancamento.class)
    public static class LancamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LancamentoController controller = (LancamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lancamentoController");
            return controller.getLancamento(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Lancamento) {
                Lancamento o = (Lancamento) object;
                return getStringKey(o.getOid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Lancamento.class.getName());
            }
        }

    }

}

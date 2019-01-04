/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
/**
 *
 * @author Ronan
 */

@ManagedBean
public class Intersecao {
    
    private int num1Faixa1; // ponto inicial da reta 1
    private int num2Faixa1; // ponto final da reta 1
    private int num1Faixa2; // ponto inicial da reta 2
    private int num2Faixa2; // ponto final da reta 1

    public Intersecao() {
    }
    
    @PostConstruct
    public void init() {
        setNum1Faixa1(0);
        setNum1Faixa2(0);
        setNum2Faixa1(0);
        setNum2Faixa2(0);
    }
    
    public int getNum1Faixa1() {
        return num1Faixa1;
    }

    public void setNum1Faixa1(int num1Faixa1) {
        this.num1Faixa1 = num1Faixa1;
    }

    public int getNum2Faixa1() {
        return num2Faixa1;
    }

    public void setNum2Faixa1(int num2Faixa1) {
        this.num2Faixa1 = num2Faixa1;
    }

    public int getNum1Faixa2() {
        return num1Faixa2;
    }

    public void setNum1Faixa2(int num1Faixa2) {
        this.num1Faixa2 = num1Faixa2;
    }

    public int getNum2Faixa2() {
        return num2Faixa2;
    }

    public void setNum2Faixa2(int num2Faixa2) {
        this.num2Faixa2 = num2Faixa2;
    }
    
    public void calculaIntersecao() {
        int x1, y1, x2, y2;
        boolean intersected = false;
        if (num1Faixa1 < num2Faixa1) {
            x1 = num1Faixa1;
            y1 = num2Faixa1;
        } else {
            x1 = num2Faixa1;
            y1 = num1Faixa1;
        }
        
        if (num1Faixa2 < num2Faixa2) {
            x2 = num1Faixa2;
            y2 = num2Faixa2;
        } else {
            x2 = num2Faixa2;
            y2 = num1Faixa2;
        }
        
        for (int j = x2; j < y2+1; j++) {
            for (int i = x1; i < y1+1; i++) {
                if (j == i) {
                    intersected = true;
                    break;
                }
            }
            
            if (intersected) {
                break;
            }
        }
       
        if (intersected) {
            FacesContext.getCurrentInstance().addMessage("MessageID", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Existe interseção entre as faixas 1 e 2."));
        } else {
            FacesContext.getCurrentInstance().addMessage("MessageID", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Não há interseção entre as faixas 1 e 2."));
        }
    }
}

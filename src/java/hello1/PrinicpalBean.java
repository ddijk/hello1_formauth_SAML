/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello1;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import weblogic.security.Security;
import weblogic.security.principal.WLSGroupImpl;
import weblogic.security.principal.WLSUserImpl;

/**
 *
 * @author DickD
 */
@ManagedBean
@RequestScoped
public class PrinicpalBean {

    String naam;
    List<String> principals = new ArrayList<String>();
    List<String> privCreds = new ArrayList<String>();

    String rol;
    String regioCode;

    @PostConstruct
    public void init() {
        Subject subject = Security.getCurrentSubject();

        for (Object o : subject.getPrivateCredentials()) {
            if (o instanceof Map) {
                // this must our custom Attribute
                Map<String, List<String>> customeAttributeMap = (Map<String, List<String>>) o;

                regioCode = customeAttributeMap.get("RegioCode").get(0);

                for (Entry<String, List<String>> entry : customeAttributeMap.entrySet()) {
                    System.out.println("key:" + entry.getKey());
                    for (String val : entry.getValue()) {
                        System.out.println("\tval:" + val);
                    }
                }
            }

            System.out.println("private cred: " + o);
            privCreds.add("" + o);
        }
        Set<Principal> allPrincipals = subject.getPrincipals();
        for (Principal principal : allPrincipals) {
            if (principal instanceof WLSGroupImpl) {
                System.out.println("found role: " + principal.getName());
                principals.add(principal.getName());
            }
            if (principal instanceof WLSUserImpl) {
                System.out.println("found user: " + principal.getName());
                naam = principal.getName();
            }
        }
    }

    /**
     * Creates a new instance of PrinicpalBean
     */
    public PrinicpalBean() {
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getRegioCode() {
        return regioCode;
    }

    public void setRegioCode(String regioCode) {
        this.regioCode = regioCode;
    }

    public List<String> getPrincipals() {
        return principals;
    }

    public void setPrincipals(List<String> principals) {
        this.principals = principals;
    }

    public String getRol() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        System.out.println("remote user: " + request.getRemoteUser());
        return "ValueOfAttributeWithSingleValue=" + request.isUserInRole("AttributeWithSingleValue=ValueOfAttributeWithSingleValue");
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<String> getPrivCreds() {
        return privCreds;
    }

    public void setPrivCreds(List<String> privCreds) {
        this.privCreds = privCreds;
    }

}

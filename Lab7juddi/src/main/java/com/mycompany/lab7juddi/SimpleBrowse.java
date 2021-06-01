/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab7juddi;

import java.rmi.RemoteException;
import java.util.List;
import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.uddi.api_v3.AccessPoint;
import org.uddi.api_v3.AuthToken;
import org.uddi.api_v3.BindingTemplate;
import org.uddi.api_v3.BindingTemplates;
import org.uddi.api_v3.BusinessDetail;
import org.uddi.api_v3.BusinessEntity;
import org.uddi.api_v3.BusinessInfos;
import org.uddi.api_v3.BusinessList;
import org.uddi.api_v3.BusinessService;
import org.uddi.api_v3.BusinessServices;
import org.uddi.api_v3.Description;
import org.uddi.api_v3.FindBusiness;
import org.uddi.api_v3.GetAuthToken;
import org.uddi.api_v3.Name;
import org.uddi.api_v3.SaveBusiness;
import org.uddi.api_v3.SaveService;
import org.uddi.api_v3.ServiceInfos;
import org.uddi.v3_service.DispositionReportFaultMessage;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDIPublicationPortType;
import org.uddi.v3_service.UDDISecurityPortType;

/**
 *
 * @author arhan
 */
public class SimpleBrowse {

    private static UDDISecurityPortType security = null;
    private static UDDIInquiryPortType inquiry = null;
    private static UDDIPublicationPortType publish = null;

    /**
     * This sets up the ws proxies using uddi.xml in META-INF
     *
     */
    public SimpleBrowse() {
        try {
            // create a manager and read the config in the archive;
            // you can use your config file name
            UDDIClient client = new UDDIClient("META-INF/simple-browse-uddi.xml");
            // a UDDIClient can be a client to multiple UDDI nodes, so
            // supply the nodeName (defined in your uddi.xml.
            // The transport can be WS, inVM, RMI etc which is defined in the uddi.xml
            Transport transport = client.getTransport("default");
            // Now you create a reference to the UDDI API
            security = transport.getUDDISecurityService();
            inquiry = transport.getUDDIInquiryService();
            publish = transport.getUDDIPublishService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        SimpleBrowse sp = new SimpleBrowse();
        sp.Browse(args);
    }

    public void Browse(String[] args) {
        try {
            String token = GetAuthKey("uddi", "uddi");
            publish(token);

            BusinessList findBusiness = GetBusinessList(token);
            PrintBusinessInfo(findBusiness.getBusinessInfos());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void publish(String token) throws RemoteException, DispositionReportFaultMessage {
        
        //create business
        SaveBusiness sb = new SaveBusiness();
        sb.setAuthInfo(token);
        BusinessEntity myBusEntity = new BusinessEntity();
        myBusEntity.setBusinessKey("abcdeffguhlklklb");
        Name myBusName = new Name();
        myBusName.setValue("My Business");
        myBusEntity.getName().add(myBusName);
        sb.getBusinessEntity().add(myBusEntity);

        BusinessDetail bd = publish.saveBusiness(sb);
        
        //create 
        String serviceKey = "fgsnjktyuioghcvhjbjk";

        SaveService ss = new SaveService();
        ss.setAuthInfo(token);
        BusinessService bs = new BusinessService();
        bs.setBusinessKey(myBusEntity.getBusinessKey());
        bs.setServiceKey(serviceKey);
        bs.setBindingTemplates(new BindingTemplates());
        BindingTemplate bt = new BindingTemplate();
        bt.setBindingKey(null);
        bt.setServiceKey(serviceKey);
        bt.setAccessPoint(new AccessPoint("http://localhost", "wsdl"));
        bs.getBindingTemplates().getBindingTemplate().add(bt);
        bs.getName().add(new Name("My business service", null));
        myBusEntity.setBusinessServices(new BusinessServices());
        myBusEntity.getBusinessServices().getBusinessService().add(bs);

        ss.getBusinessService().add(bs);
        publish.saveService(ss);
    }
    
    private BusinessList GetBusinessList(String token) throws Exception {
        FindBusiness fb = new FindBusiness();
        fb.setAuthInfo(token);
        org.uddi.api_v3.FindQualifiers fq = new org.uddi.api_v3.FindQualifiers();
        fq.getFindQualifier().add(UDDIConstants.APPROXIMATE_MATCH);

        fb.setFindQualifiers(fq);
        Name searchname = new Name();
        searchname.setValue(UDDIConstants.WILDCARD);
        fb.getName().add(searchname);
        BusinessList findBusiness = inquiry.findBusiness(fb);
        return findBusiness;

    }

    private enum AuthStyle {
        HTTP_BASIC, HTTP_DIGEST, HTTP_NTLM, UDDI_AUTH, HTTP_CLIENT_CERT
    }
    
    private void PrintBusinessInfo(BusinessInfos businessInfos) {
        if (businessInfos == null) {
            System.out.println("No data returned");
        } else {
            for (int i = 0; i < businessInfos.getBusinessInfo().size(); i++) {
                System.out.println("===============================================");
                System.out.println("Business Key: " + businessInfos.getBusinessInfo().get(i).getBusinessKey());
                System.out.println("Name: " + ListToString(businessInfos.getBusinessInfo().get(i).getName()));

                System.out.println("Description: " + ListToDescString(businessInfos.getBusinessInfo().get(i).getDescription()));
                System.out.println("Services:");
                if (businessInfos.getBusinessInfo().get(i).getServiceInfos()!=null) PrintServiceInfo(businessInfos.getBusinessInfo().get(i).getServiceInfos());
            }
        }
    }
    
    private void PrintServiceInfo(ServiceInfos serviceInfos) {
        for (int i = 0; i < serviceInfos.getServiceInfo().size(); i++) {
            System.out.println("-------------------------------------------");
            System.out.println("Service Key: " + serviceInfos.getServiceInfo().get(i).getServiceKey());
            System.out.println("Owning Business Key: " + serviceInfos.getServiceInfo().get(i).getBusinessKey());
            System.out.println("Name: " + ListToString(serviceInfos.getServiceInfo().get(i).getName()));
        }
    }
    
    private String ListToString(List<Name> name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.size(); i++) {
            sb.append(name.get(i).getValue()).append(" ");
        }
        return sb.toString();
    }

    private String ListToDescString(List<Description> name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.size(); i++) {
            sb.append(name.get(i).getValue()).append(" ");
        }
        return sb.toString();
    }
    /**
     * * Gets a UDDI style auth token, otherwise, appends credentials to the*
     * ws proxies (not yet implemented)** @param username* @param password
     *
     *
     * @param style
     *
     * 87* @ return
     */
    private String GetAuthKey(String username, String password) {
        try {
            GetAuthToken getAuthTokenRoot = new GetAuthToken();
            getAuthTokenRoot.setUserID(username);
            getAuthTokenRoot.setCred(password);// Making API call that retrieves the authentication token for the user.
            AuthToken rootAuthToken = security.getAuthToken(getAuthTokenRoot);
            System.out.println(username + " AUTHTOKEN = (don't log auth tokens!");
            return rootAuthToken.getAuthInfo();
        } catch (Exception ex) {
            System.out.println("Could not authenticate with the provided credentials " + ex.getMessage());
        }
        return null;
    }
}

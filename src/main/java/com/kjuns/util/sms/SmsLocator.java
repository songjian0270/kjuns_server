package com.kjuns.util.sms;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

/**
 * <b>Function: </b>  配置soap请求的实体类
 * @author James
 * @date 2015-04-12
 * @file SmsLocator.java
 * @package com.gtbang.util.sms
 * @project gtbang
 * @version 1.0
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class SmsLocator extends Service implements Sms {

    // 配置soap访问地址
    public String smsSoapAddress;
    
    public SmsLocator(String smsSoapAddress) {
    	this.smsSoapAddress = smsSoapAddress;
    }

    public SmsLocator(EngineConfiguration config) {
        super(config);
    }

    public SmsLocator(String wsdlLoc, QName sName) throws ServiceException {
        super(wsdlLoc, sName);
    }

    public java.lang.String getSmsSoapAddress() {
        return smsSoapAddress;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SmsSoapWSDDServiceName = "SmsSoap";

    public java.lang.String getSmsSoapWSDDServiceName() {
        return SmsSoapWSDDServiceName;
    }

    public void setSmsSoapWSDDServiceName(java.lang.String name) {
        SmsSoapWSDDServiceName = name;
    }

    public SmsSoap getSmsSoap() throws ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new URL(smsSoapAddress);
        }
        catch (MalformedURLException e) {
            throw new ServiceException(e);
        }
        return getSmsSoap(endpoint);
    }

    public SmsSoap getSmsSoap(URL portAddress) throws ServiceException {
        try {
        	SmsSoapStub _stub = new SmsSoapStub(portAddress, this);
            _stub.setPortName(getSmsSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSmsSoapEndpointAddress(String address) {
    	smsSoapAddress = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
	public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
        try {
            if (SmsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
            	SmsSoapStub _stub = new SmsSoapStub(new URL(smsSoapAddress), this);
                _stub.setPortName(getSmsSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("SmsSoap".equals(inputPortName)) {
            return getSmsSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new QName("http://tempuri.org/", "Sms");
    }

    private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new QName("http://tempuri.org/", "SmsSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws ServiceException {
		if ("SmsSoap".equals(portName)) {
		    setSmsSoapEndpointAddress(address);
		}else { // Unknown Port Name
		    throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

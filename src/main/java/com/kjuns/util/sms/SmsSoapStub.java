package com.kjuns.util.sms;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.SerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

/**
 * 具体方法的实现类
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
public class SmsSoapStub extends Stub implements SmsSoap {
	
    private static final String NAME_SPACE = "http://tempuri.org/";
    
    private static String NAME_SPACE_PARAMS = "http://www.w3.org/2001/XMLSchema";
    
	private Vector cachedSerClasses = new Vector();
    private Vector cachedSerQNames = new Vector();
    
    private Vector cachedSerFactories = new Vector();
    private Vector cachedDeserFactories = new Vector();

    static OperationDesc [] _operations;

    static {
        _operations = new OperationDesc[4];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        OperationDesc oper;
        ParameterDesc param;

        //第一个方法：MongateSendSubmit
        oper = new OperationDesc();
        oper.setName("MongateSendSubmit");
        param = new ParameterDesc(new QName(NAME_SPACE, "UserId"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName(NAME_SPACE, "Password"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName(NAME_SPACE, "PszMobis"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName(NAME_SPACE, "PszMsg"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName(NAME_SPACE, "iMobiCount"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "int"), int.class, false, false);
        oper.addParameter(param);
        
        param = new ParameterDesc(new QName(NAME_SPACE, "PszSubPort"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param); 
        
        param = new ParameterDesc(new QName(NAME_SPACE, "MsgId"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param); 
        
        oper.setReturnType(new QName(NAME_SPACE_PARAMS, "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new QName(NAME_SPACE, "MongateSendSubmitResult"));
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);
        _operations[0] = oper;
        
        //第二个方法：MongateMULTIXSend
        oper = new OperationDesc();
        oper.setName("MongateMULTIXSend");
        param = new ParameterDesc(new QName(NAME_SPACE, "UserId"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName(NAME_SPACE, "Password"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName(NAME_SPACE, "multixmt"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);        
        
        oper.setReturnType(new QName(NAME_SPACE_PARAMS, "string"));
        oper.setReturnClass(String.class);
        oper.setReturnQName(new QName(NAME_SPACE, "MongateMULTIXSendResult"));
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);
        _operations[1] = oper;
        
        //第三个方法MongateQueryBalance
        oper = new OperationDesc();
        oper.setName("MongateQueryBalance");
        param = new ParameterDesc(new QName(NAME_SPACE, "UserId"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName(NAME_SPACE, "Password"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        
        oper.setReturnType(new QName(NAME_SPACE_PARAMS, "int"));
        oper.setReturnClass(int.class);
        oper.setReturnQName(new QName(NAME_SPACE, "MongateQueryBalanceResult"));
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);
        _operations[2] = oper;

        //第四个方法：MongateGetDeliver 
        oper = new OperationDesc();
        oper.setName("MongateGetDeliver");
        param = new ParameterDesc(new QName(NAME_SPACE, "UserId"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName(NAME_SPACE, "Password"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName(NAME_SPACE, "IReqType"), ParameterDesc.IN, new QName(NAME_SPACE_PARAMS, "int"), int.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);

        oper.setReturnType(new QName(NAME_SPACE_PARAMS, "string[]"));
        oper.setReturnClass(String[].class);
        oper.setReturnQName(new QName(NAME_SPACE, "MongateGetDeliverResult"));
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);
        _operations[3] = oper;

    }

    public SmsSoapStub() throws AxisFault {
         this(null);
    }

    public SmsSoapStub(URL endpointURL, Service service) throws AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SmsSoapStub(Service service) throws AxisFault {
        if (service == null) {
            super.service = new Service();
        } else {
            super.service = service;
        }
        ((Service)super.service).setTypeMappingVersion("1.2");
    }

    public Call createCall() throws RemoteException {
        try {
            Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }

            synchronized (this) {
                if (firstCall()) {
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        Class cls = (Class) cachedSerClasses.get(i);
                        QName qName = (QName) cachedSerQNames.get(i);
                        Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            Class sf = (Class)cachedSerFactories.get(i);
                            Class df = (Class)cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof SerializerFactory) {
                            SerializerFactory sf = (SerializerFactory)cachedSerFactories.get(i);
                            DeserializerFactory df = (DeserializerFactory) cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (Throwable _t) {
            throw new  AxisFault("Failure trying to get the Call object", _t);
        }
    }
    

	/**
	 * 短信息发送接口
	 * 重要说明：相同信息内容的号码一定要打包发送，一次可以发送100个号码。如果对于相同的信息一条一条发送，系统将会自动停止用户帐号。
	 * 返回值：
			错误描述对应说明
			发送成功：信息编号，如：-8485643440204283743或1485643440204283743
			错误返回： 
			-1	参数为空。信息、电话号码等有空指针，登陆失败
			-2	电话号码个数超过100
			-10	申请缓存空间失败
			-11	电话号码中有非数字字符
			-12	有异常电话号码
			-13	电话号码个数与实际个数不相等
			-14	实际号码个数超过100
			-101	发送消息等待超时
			-102	发送或接收消息失败
			-103	接收消息超时
			-200	其他错误
			-999	web服务器内部错误
			其他值见附录
	 */
	public String MongateSendSubmit(String userId, String password,
			String pszMobis, String pszMsg, int iMobiCount, String pszSubPort,
			String MsgId) throws RemoteException {
	    if (super.cachedEndpoint == null) {
            throw new  NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI(NAME_SPACE + "MongateSendSubmit");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName(NAME_SPACE, "MongateSendSubmit"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {  
			 Object _resp = _call.invoke(new Object[] { userId, password,pszMobis,pszMsg,iMobiCount,pszSubPort,MsgId});
		
		        if (_resp instanceof RemoteException) {
		            throw (RemoteException)_resp;
		        }
		        else {
		            extractAttachments(_call);
		            try {
		                return (String)_resp;
		            } catch (Exception _exception) {
		                return ((String) JavaUtils.convert(_resp, String.class));
		            }
		        }
		  } catch (AxisFault axisFaultException) {
		       throw axisFaultException;
		  } 
	}

    /**
     * 短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号）
     * 例：对手机号13800138000，使用通道号10657120395789 41，发送一条流水号为457894132578945的内容为“你好,欢迎使用!”的短信。其短信包为：
     *流水号+完整的通道接入号: “457894132578945/1065712039578941/13800138000/xOO6wyy7ttOtyrnTwyE=”
     *流水号+通道扩展子号: “457894132578945/41/13800138000/xOO6wyy7ttOtyrnTwyE=”
     *不需流水号 : “0/1065712039578941/13800138000/xOO6wyy7ttOtyrnTwyE=”
                  “0/41/13800138000/xOO6wyy7ttOtyrnTwyE=”
     *注意：由于单个短信结构体中各元素间，以及短信包中各个短信结构体间采用的分隔符分隔，所以要求短信结构体中各元素的值中不能包含分隔符（,和/），否则将导致解析错误。
                               短信包中最大包含200个短信结构体，超过200个时200个以后的将丢失。
             返回值：
		错误描述对应说明
		发送成功：信息编号，如：-8485643440204283743或1485643440204283743
		错误返回： 
		-1 	参数为空。信息、电话号码等有空指针，登陆失败
		-11	电话号码中有非数字字符
		-12	有异常电话号码
		-101	发送消息等待超时
		-102	发送或接收消息失败
		-103	接收消息超时
		-200	其他错误
		-999	web服务器内部错误                         
     */
	public String MongateMULTIXSend(String userId, String password,String multix_mt) throws RemoteException {
	    if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI(NAME_SPACE + "MongateMULTIXSend");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName(NAME_SPACE, "MongateMULTIXSend"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {  
			 Object _resp = _call.invoke(new Object[] { userId, password,multix_mt});
		        if (_resp instanceof RemoteException) {
		            throw (RemoteException)_resp;
		        }
		        else {
		            extractAttachments(_call);
		            try {
		                return (String)_resp;
		            } catch (Exception _exception) {
		                return ((String) JavaUtils.convert(_resp, String.class));
		            }
		        }
		  } catch (AxisFault axisFaultException) {
		       throw axisFaultException;
		  } 
	}

    /**
     * 短信息发送接口（不同内容群发，可自定义不同流水号，自定义不同扩展号）
     * 例：对手机号13800138000，使用通道号10657120395789 41，发送一条流水号为457894132578945的内容为“你好,欢迎使用!”的短信。其短信包为：
     *流水号+完整的通道接入号: “457894132578945/1065712039578941/13800138000/xOO6wyy7ttOtyrnTwyE=”
     *流水号+通道扩展子号: “457894132578945/41/13800138000/xOO6wyy7ttOtyrnTwyE=”
     *不需流水号 : “0/1065712039578941/13800138000/xOO6wyy7ttOtyrnTwyE=”
                  “0/41/13800138000/xOO6wyy7ttOtyrnTwyE=”
     *注意：由于单个短信结构体中各元素间，以及短信包中各个短信结构体间采用的分隔符分隔，所以要求短信结构体中各元素的值中不能包含分隔符（,和/），否则将导致解析错误。
                               短信包中最大包含200个短信结构体，超过200个时200个以后的将丢失。
             返回值：
		错误描述对应说明
		发送成功：信息编号，如：-8485643440204283743或1485643440204283743
		错误返回： 
		-1 	参数为空。信息、电话号码等有空指针，登陆失败
		-11	电话号码中有非数字字符
		-12	有异常电话号码
		-101	发送消息等待超时
		-102	发送或接收消息失败
		-103	接收消息超时
		-200	其他错误
		-999	web服务器内部错误                         
     */
	public String MongateMULTIXSend(String userId, String password,String multix_mt,Call _call) throws RemoteException {
	    if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI(NAME_SPACE + "MongateMULTIXSend");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName(NAME_SPACE, "MongateMULTIXSend"));
        _call.setTimeout(40000);
        setRequestHeaders(_call);
        setAttachments(_call);
		 try {  
			 Object _resp = _call.invoke(new Object[] { userId, password,multix_mt});
		
		        if (_resp instanceof RemoteException) {
		            throw (RemoteException)_resp;
		        }
		        else {
		            extractAttachments(_call);
		            try {
		                return (String)_resp;
		            } catch (Exception _exception) {
		                return ((String) JavaUtils.convert(_resp, String.class));
		            }
		        }
		  } catch (AxisFault axisFaultException) {
		       throw axisFaultException;
		  } 
	}
	
    /**
     * 查询余额接口
     * 返回值：
			正数或0：帐户可发送条数
			-1    登陆失败
			其他错误见附录
     */
    public int MongateQueryBalance(String userId, String password) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI(NAME_SPACE + "MongateQueryBalance");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName(NAME_SPACE, "MongateQueryBalance"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {     
			   Object _resp = _call.invoke(new Object[] {userId, password});
		
		        if (_resp instanceof RemoteException) {
		            throw (RemoteException)_resp;
		        }
		        else {
		            extractAttachments(_call);
		            try {
		                return (Integer) _resp;
		            } catch (Exception _exception) {
		                return (Integer) JavaUtils.convert(_resp, int.class);
		            }
		        }
		  } catch (AxisFault axisFaultException) {
		      throw axisFaultException;
		  }
    }
    
    /**
     * 查询余额接口
     * 返回值：
			正数或0：帐户可发送条数
			-1    登陆失败
			其他错误见附录
     */
    public int MongateQueryBalance(String userId, String password,Call _call) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI(NAME_SPACE + "MongateQueryBalance");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName(NAME_SPACE, "MongateQueryBalance"));
        setRequestHeaders(_call);
        _call.setTimeout(40000);
        setAttachments(_call);
		 try {     
			   Object _resp = _call.invoke(new Object[] {userId, password});
		
		        if (_resp instanceof RemoteException) {
		            throw (RemoteException)_resp;
		        }
		        else {
		            extractAttachments(_call);
		            try {
		                return (Integer) _resp;
		            } catch (Exception _exception) {
		                return (Integer) JavaUtils.convert(_resp, int.class);
		            }
		        }
		  } catch (AxisFault axisFaultException) {
		      throw axisFaultException;
		  }
    }


	/**
	 * 获取上行/状态报告等
	 * 返回值：
			null 无信息
			接收信息内容列表(最高维数为500)格式说明：
			信息类型(上行标志0),日期,时间,上行源号码,上行目标通道号,*,*,上行信息内容 
			或
			信息类型(状态报告标志1),日期,时间,信息编号,通道号,手机号,MongateSendSubmit时填写的MsgId,*,状态值,详细错误原因
			例如：
			1,2008-01-23 15:43:34,15986756631,10657302056780408,*,*,上行信息内容1
			2,2008-01-23 15:43:34,0518153837115735,10657302056780408,13265661403,456123457895210124,
			*,0,DELIVRD
			注：格式中的*号是备用字段 第一标志位的0表示上行和状态报告，1表示上行，2表示状态报告 
	 */
	public String[] MongateGetDeliver(String userId, String password,
			int iReqType) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI(NAME_SPACE + "MongateGetDeliver");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName(NAME_SPACE, "MongateGetDeliver"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {  
			 Object _resp = _call.invoke(new Object[] { userId, password,iReqType});
		
		        if (_resp instanceof RemoteException) {
		            throw (RemoteException)_resp;
		        }
		        else {
		            extractAttachments(_call);
		            try {
		                return (String[])_resp;
		            } catch (Exception _exception) {
		                return ((String[]) JavaUtils.convert(_resp, String.class));
		            }
		        }
		  } catch (AxisFault axisFaultException) {
		       throw axisFaultException;
		  } 
	}
	
	/**
	 * 获取上行/状态报告等
	 * 返回值：
			null 无信息
			接收信息内容列表(最高维数为500)格式说明：
			信息类型(上行标志0),日期,时间,上行源号码,上行目标通道号,*,*,上行信息内容 
			或
			信息类型(状态报告标志1),日期,时间,信息编号,通道号,手机号,MongateSendSubmit时填写的MsgId,*,状态值,详细错误原因
			例如：
			1,2008-01-23 15:43:34,15986756631,10657302056780408,*,*,上行信息内容1
			2,2008-01-23 15:43:34,0518153837115735,10657302056780408,13265661403,456123457895210124,
			*,0,DELIVRD
			注：格式中的*号是备用字段 第一标志位的0表示上行和状态报告，1表示上行，2表示状态报告 
	 */
	public String[] MongateGetDeliver(String userId, String password,
			int iReqType,Call _call) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI(NAME_SPACE + "MongateGetDeliver");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName(NAME_SPACE, "MongateGetDeliver"));
        _call.setTimeout(40000);
        
        setRequestHeaders(_call);
        setAttachments(_call);
		 try {  
			 Object _resp = _call.invoke(new Object[] { userId, password,iReqType});
		
		        if (_resp instanceof RemoteException) {
		            throw (RemoteException)_resp;
		        }
		        else {
		            extractAttachments(_call);
		            try {
		                return (String[])_resp;
		            } catch (Exception _exception) {
		                return ((String[]) JavaUtils.convert(_resp, String.class));
		            }
		        }
		  } catch (AxisFault axisFaultException) {
		       throw axisFaultException;
		  } 
	}

	
	/**
	 * 短信息发送接口
	 * 重要说明：相同信息内容的号码一定要打包发送，一次可以发送100个号码。如果对于相同的信息一条一条发送，系统将会自动停止用户帐号。
	 * 返回值：
			错误描述对应说明
			发送成功：信息编号，如：-8485643440204283743或1485643440204283743
			错误返回： 
			-1	参数为空。信息、电话号码等有空指针，登陆失败
			-2	电话号码个数超过100
			-10	申请缓存空间失败
			-11	电话号码中有非数字字符
			-12	有异常电话号码
			-13	电话号码个数与实际个数不相等
			-14	实际号码个数超过100
			-101	发送消息等待超时
			-102	发送或接收消息失败
			-103	接收消息超时
			-200	其他错误
			-999	web服务器内部错误
			其他值见附录
	 */
	public String MongateSendSubmit(String userId, String password,
			String pszMobis, String pszMsg, int iMobiCount, String pszSubPort,
			String MsgId,Call _call) throws RemoteException {
	    if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI(NAME_SPACE + "MongateSendSubmit");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName(NAME_SPACE, "MongateSendSubmit"));
        _call.setTimeout(40000);
        setRequestHeaders(_call);
        setAttachments(_call);
		 try {  
			 Object _resp = _call.invoke(new Object[] { userId, password,pszMobis,pszMsg,iMobiCount,pszSubPort,MsgId});
		
		        if (_resp instanceof RemoteException) {
		            throw (RemoteException)_resp;
		        }
		        else {
		            extractAttachments(_call);
		            try {
		                return (String)_resp;
		            } catch (Exception _exception) {
		                return ((String) JavaUtils.convert(_resp, String.class));
		            }
		        }
		  } catch (AxisFault axisFaultException) {
		       throw axisFaultException;
		  } 
	}

	
 
}

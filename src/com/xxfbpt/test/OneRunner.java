package com.xxfbpt.test;

import java.net.URL;

import org.codehaus.xfire.client.Client;

import com.xxfbpt.util.DESCorder;

public class OneRunner implements Runnable {
	public   int i = 0;
	public void run() {
		try {
			//00-用户登录  01-用户登出  02-密码变更 03-缴费查询 04-缴费处理 05-整体对账查询 06-明细对账查询
			String code = "03";
			//用户代号
			String yhdh = "00001";
			//用户代号对应的登录密码
			String yhmm = "64685403";
			String hashCode = "";
			String reqxml = "<request><head><yhdh>pa</yhdh><ip>127.0.0.1</ip><lsh>0001</lsh></head><body><jkbh>4403041091343671</jkbh><cxsj>20140731101352</cxsj><cxly>01</cxly><cxlybh>01</cxlybh><ywlx>01</ywlx></body></request>";
			hashCode = DESCorder.encodeMD5(reqxml+yhmm);    //MD5加密：请求报文+用户代号对应的登录密码，再进行MD5加密
			reqxml = DESCorder.encryptModeToString(reqxml, yhmm); //3DES加密后转成Base64码
			
			for (int i = 0; i < 1; i++) {
				//String xml = "<request><head><yhdh>pa</yhdh><ip>127.0.0.1</ip><lsh>0001</lsh></head><body><jkbh>4403041091343671</jkbh><cxsj>20140725101352</cxsj><cxly>01</cxly><cxlybh>01</cxlybh><ywlx>01</ywlx></body></request>";
				Client client = new Client(new URL(
				"http://100.100.3.157:8089/dwfbpt/services/dwfbptservice?wsdl"));
				Object[] results = client.invoke("SendUnionRequest",
						new Object[] { code, yhdh, hashCode, reqxml });
				client.close();
//				String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><QueryCondition><jdsbh>440305270097751</jdsbh><wfbh></wfbh><jszh></jszh><dabh></dabh><jkbj></jkbj></QueryCondition></root>";
//				Client client = new Client(new URL(
//				"http://100.100.21.10/viofee/viofeeservice.asmx?wsdl"));
//				Object[] results = client.invoke("InterfaceMonitor",
//						new Object[] { "04", "781A0909030217040815E0F7869D848989EA879BFCC998ED8DC76D72692E636E", "04C01", xml });
				System.out.println(Thread.currentThread().getName()+":"+results[0]);
				//System.out.println(i);	
			}
			
			//00-用户登录  01-用户登出  02-密码变更 03-缴费查询 04-缴费处理 05-整体对账查询 06-明细对账查询
//			String code = "03";
//			//用户代号
//			String yhdh = "test";
//			//用户登录后返回的身份验证码
//			String yhmy = "369258";
//			String hashCode = "";
//			String reqxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><head><yhdh>pa</yhdh><ip>127.0.0.1</ip><lsh>0001</lsh></head><body><jkbh>12345678</jkbh><cxsj>2014-07-21</cxsj><cxly>查询来源|string|按客户端类型分，详见备注</cxly><cxlybh>查询来源编号|string|查询来源的编号</cxlybh><ywlx>查询业务类型|string|包含车驾管规费，交通罚款等，详见备注</ywlx></body></request>";
//			hashCode = DESCorder.encodeMD5(reqxml+yhmy);    //MD5加密：请求报文+用户代号登录后返回的身份验证码，再进行MD5加密
//			reqxml = DESCorder.encryptModeToString(reqxml, yhmy); //3DES加密后转成Base64码
//			Client client = new Client(new URL(
//			"http://100.100.3.157:8089/dwfbpt/services/dwfbptservice?wsdl"));
//			Object[] results = client.invoke("SendUnionRequest",
//					new Object[] { code, yhdh, hashCode, reqxml });
//			System.out.println(results[0]);
			
			
			//交通违章查询Webservice根据决定书编号16位  --440300270040814  
//			String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><QueryCondition><jdsbh>440305270097751</jdsbh><wfbh></wfbh><jszh></jszh><dabh></dabh><jkbj></jkbj></QueryCondition></root>";
//			Client client = new Client(new URL(
//			"http://100.100.21.10/viofee/viofeeservice.asmx?wsdl"));
//			Object[] results = client.invoke("InterfaceMonitor",
//					new Object[] { "04", "781A0909030217040815E0F7869D848989EA879BFCC998ED8DC76D72692E636E", "04C01", xml });
//			System.out.println(results[0]);
			
			
			//交通违章查询Webservice根据序号12位
//			String xml2 = "<?xml version=\"1.0\" encoding=\"gbk\"?><root><QueryCondition><hpzl></hpzl><hphm></hphm><xh>4403160000015637</xh><clbj></clbj></QueryCondition></root>";
//			Client client2 = new Client(new URL(
//			"http://100.100.21.10/viofee/viofeeservice.asmx?wsdl"));
//			Object[] results2 = client2.invoke("InterfaceMonitor",
//					new Object[] { "04", "781A090903021704081587D6E7E1879D82DAE9F1FD9AEE848099A2C7A49D636E", "04C03", xml2 });
//			System.out.println(results2[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized int add(){
		return i++;
	}

}

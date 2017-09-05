package com.alibaba.dingtalk.openapi.demo.message;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.MessageSendResult;
import com.dingtalk.open.client.api.service.corp.MessageService;

public class MessageHelper {

	public static class Receipt {
		String invaliduser;
		String invalidparty;
	}
	
	public static Receipt send(String accessToken, LightAppMessageDelivery delivery) 
			throws Exception {
		MessageService messageService = ServiceFactory.getInstance().getOpenService(MessageService.class);
		MessageSendResult reulst = messageService.sendToCorpConversation(accessToken, delivery.touser, 
				delivery.toparty, delivery.agentid, delivery.msgType, delivery.message);
		Receipt receipt = new Receipt();
		receipt.invaliduser = reulst.getInvaliduser();
		receipt.invalidparty = reulst.getInvalidparty();
		return receipt;
		}
	
	
	public static String send(String accessToken, ConversationMessageDelivery delivery) 
		throws Exception {
		MessageService messageService = ServiceFactory.getInstance().getOpenService(MessageService.class);
		return  messageService.sendToNormalConversation(accessToken, delivery.sender, 
				delivery.cid, delivery.msgType, delivery.message);
	}
}

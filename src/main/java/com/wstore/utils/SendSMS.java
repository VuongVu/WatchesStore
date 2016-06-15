package com.wstore.utils;

import com.twilio.sdk.Twilio;
import com.twilio.sdk.creator.api.v2010.account.MessageCreator;
import com.twilio.sdk.resource.api.v2010.account.Message;
import com.twilio.sdk.type.PhoneNumber;

public class SendSMS {
	// Find your Account Sid and Token at twilio.com/console
	public static final String ACCOUNT_SID = "{{ACf3fa29ce497c58e800f84bd76f619e97}}";
	public static final String AUTH_TOKEN = "{{c3ab6dab46b81a20ce7b465a646c1c03}}";

	public static void main(String[] args) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Message message = new MessageCreator(
				ACCOUNT_SID,
				new PhoneNumber("+841642799221"), // TO number
				new PhoneNumber("+84983064443"), // From Twilio number
				"Hello from Java"
				).execute();

		System.out.println(message.getSid());
	}
}

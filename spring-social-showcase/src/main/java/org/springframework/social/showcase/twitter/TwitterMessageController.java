/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.showcase.twitter;

import javax.inject.Inject;
import javax.inject.Provider;

import org.springframework.social.twitter.api.TwitterApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TwitterMessageController {

	private final Provider<TwitterApi> twitterApiProvider;
	
	@Inject
	public TwitterMessageController(Provider<TwitterApi> twitterApiProvider) {
		this.twitterApiProvider = twitterApiProvider;
	}
	
	@RequestMapping(value="/twitter/messages", method=RequestMethod.GET)
	public String inbox(Model model) {
		model.addAttribute("directMessages", getTwitterApi().directMessageOperations().getDirectMessagesReceived());
		model.addAttribute("dmListType", "Received");
		model.addAttribute("messageForm", new MessageForm());
		return "twitter/messages";
	}

	@RequestMapping(value="/twitter/messages/sent", method=RequestMethod.GET)
	public String sent(Model model) {
		model.addAttribute("directMessages", getTwitterApi().directMessageOperations().getDirectMessagesSent());
		model.addAttribute("dmListType", "Sent");
		model.addAttribute("messageForm", new MessageForm());
		return "twitter/messages";
	}

	@RequestMapping(value="/twitter/messages", method=RequestMethod.POST)
	public String sent(MessageForm message) {
		getTwitterApi().directMessageOperations().sendDirectMessage(message.getTo(), message.getText());
		return "redirect:/twitter/messages";
	}
	
	private TwitterApi getTwitterApi() {
		return twitterApiProvider.get();
	}

}
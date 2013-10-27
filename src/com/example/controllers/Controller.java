package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

abstract class Controller {
	
	private final List<Handler> outboxHandlers = new ArrayList<Handler>();

	public Controller() {
		
	}
	
	public void dispose() {}
	
	abstract public boolean handleMessage(int what, Object data);

	public boolean handleMessage(int what) {
		return handleMessage(what, null);
	}
	
	public final void addOutboxHandler(Handler handler) {
		outboxHandlers.add(handler);
	}

	public final void removeOutboxHandler(Handler handler) {
		outboxHandlers.remove(handler);
	}
	
	/**
	 * Sends messages to the view
	 * 
	 * It doesn't care what the view thinks or does. There is no need for a response so we 
	 * can use Android's messaging system (Handler), which handles this task asynchronously.
	 */
	protected final void notifyOutboxHandlers(int what, int arg1, int arg2, Object obj) {
		if (!outboxHandlers.isEmpty()) {
			for (Handler handler : outboxHandlers) {
				Message msg = Message.obtain(handler, what, arg1, arg2, obj);
				msg.sendToTarget();
			}
		}
	}
}

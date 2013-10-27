package com.example.vos;

public interface EasyObservable<T> {
	void addListener(OnChangeListener<T> listener);
	void removeListener(OnChangeListener<T> listener);
}

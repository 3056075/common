package com.zm.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

public interface BaseDao<T> {
	public SessionFactory getSessionFactory();
	
	public Serializable save(T o);

	public void delete(T o);

	public void update(T o);

	public void saveOrUpdate(T o);

	public List<T> findAll();
	
	public List<T> find(String hql);

	public List<T> find(String hql, Object... param);

	public List<T> find(String hql, List<Object> param);
	
	public List<T> find(String hql, Map<String,Object> param);

	public List<T> find(String hql, Integer page, Integer rows, Object... param);

	public List<T> find(String hql, List<Object> param, Integer page,
			Integer rows);
	
	public List<T> find(String hql, Map<String,Object> param, Integer page,
			Integer rows);

	public T get(Serializable id);
	
	public T get(Class<T> c, Serializable id);

	public T get(String hql, Object... param);

	public T get(String hql, List<Object> param);
	
	public T get(String hql, Map<String,Object> param);

	public Long count(String hql);

	public Long count(String hql, Object... param);

	public Long count(String hql, List<Object> param);
	
	public Long count(String hql, Map<String,Object> param);

	public Integer executeHql(String hql);

	public Integer executeHql(String hql, Object... param);

	public Integer executeHql(String hql, List<Object> param);
	
	public Integer executeHql(String hql, Map<String,Object> param);

	List<Object> commonfind(String hql);
}

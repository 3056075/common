package com.zm.common.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zm.common.constant.StringConstant;
import com.zm.common.dao.BaseDao;
import com.zm.common.pagination.BasePagination;
import com.zm.common.utils.ReflectUtils;
import com.zm.common.utils.StringUtils;


@SuppressWarnings("unchecked")
@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
	private SessionFactory sessionFactory;
	
	private Class<T> getPersistentClass(){
		return (Class<T>) ReflectUtils.getSuperClassGenricType(getClass(), 0); 
	}
	
	protected void appendSort(StringBuilder hql,String aliasName,
			BasePagination<? extends Object> page) {
		if (null == page) {
			return;
		}
		String sort = page.getSort();
		String dir = page.getDir();
		if(!StringConstant.ASC.equals(dir)){
			dir = StringConstant.DESC;
		}
		if (StringUtils.isNotBlank(sort)) {
			hql.append(" order by ");
			if (StringUtils.isNotBlank(aliasName)) {
				hql.append(aliasName).append(".");
			}
			hql.append(sort).append(" ").append(dir);
		}
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}

	@Override
	public void delete(T o) {
		this.getCurrentSession().delete(o);

	}

	@Override
	public void update(T o) {
		this.getCurrentSession().update(o);

	}

	@Override
	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);

	}

	public List<T> findAll(){
		return find(" from " + getPersistentClass().getName());
	};
	
	@Override
	public List<T> find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}
	
	@Override
	public List<Object> commonfind(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	@Override
	public List<T> find(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}
	
	@Override
	public List<T> find(String hql, Map<String,Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for ( Entry<String, Object> entity:param.entrySet()) {
				q.setParameter(entity.getKey(), entity.getValue());
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(String hql, Integer page, Integer rows, Object... param) {
		if (page == null || page < 0) {
			page = 0;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult((page) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> find(String hql, List<Object> param, Integer page,
			Integer rows) {
		if (page == null || page <01) {
			page = 0;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page) * rows).setMaxResults(rows).list();
	}
	
	@Override
	public List<T> find(String hql, Map<String,Object> param, Integer page,
			Integer rows) {
		if (page == null || page < 0) {
			page = 0;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for ( Entry<String, Object> entity:param.entrySet()) {
				q.setParameter(entity.getKey(), entity.getValue());
			}
		}
		return q.setFirstResult((page) * rows).setMaxResults(rows).list();
	}

	@Override
	public T get(Serializable id){
		return get(getPersistentClass(),id);
	}
	
	@Override
	public T get(Class<T> c, Serializable id) {
		if(null==id){
			return null;
		}
		return (T) this.getCurrentSession().get(c, id);
	}

	@Override
	public T get(String hql, Object... param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public T get(String hql, List<Object> param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public T get(String hql, Map<String,Object> param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Long count(String hql) {
		return (Long) this.getCurrentSession().createQuery(hql).uniqueResult();
	}

	@Override
	public Long count(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}
	
	@Override
	public Long count(String hql, Map<String,Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for ( Entry<String, Object> entity:param.entrySet()) {
				q.setParameter(entity.getKey(), entity.getValue());
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public Integer executeHql(String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	@Override
	public Integer executeHql(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.executeUpdate();
	}

	@Override
	public Integer executeHql(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.executeUpdate();
	}
	
	@Override
	public Integer executeHql(String hql, Map<String,Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for ( Entry<String, Object> entity:param.entrySet()) {
				q.setParameter(entity.getKey(), entity.getValue());
			}
		}
		return q.executeUpdate();
	}

}

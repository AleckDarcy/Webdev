  package com.sangmall.dao;
  
  import com.sangmall.util.Util;
  import java.util.List;
  import java.util.Map;
  import org.apache.commons.lang3.StringUtils;
  import org.hibernate.Criteria;
  import org.hibernate.SQLQuery;
  import org.hibernate.SessionFactory;
  import org.hibernate.Transaction;
  import org.hibernate.classic.Session;
  import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;
  
  public class TemplateDao
  {
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory)
    {
      this.sessionFactory = sessionFactory;
    }
    
    @Transactional
    public void addModel(Object model)
    {
    //  Session s = this.sessionFactory.openSession();
      Session s = sessionFactory.getCurrentSession();
      
   //   Transaction t = s.beginTransaction();
      s.save(model);
   //   t.commit();
   //   s.close();
    }
    
    @Transactional
    public void deleteModel(Object model)
    {
    //  Session s = this.sessionFactory.openSession();
      Session s = this.sessionFactory.getCurrentSession();
    //  Transaction t = s.beginTransaction();
      s.delete(model);
    //  t.commit();
    //  s.close();
    }
    
    @Transactional
    public Object findModelById(Class arg0, int id)
    {
     // Session s = this.sessionFactory.openSession();
      Session s = this.sessionFactory.getCurrentSession();
    //  Transaction t = s.beginTransaction();
      Object o = s.get(arg0, Integer.valueOf(id));
    //  t.commit();
    //  s.close();
      
      return o;
    }
    
    @Transactional
    public Object selectModel(Object model)
    {
      //Session s = this.sessionFactory.openSession();
      Session s = this.sessionFactory.getCurrentSession();
      //s.beginTransaction();
      Example example = Example.create(model);
      Criteria criteria = s.createCriteria(model.getClass()).add(example);
      List l = criteria.list();
      //s.getTransaction().commit();
      if (l.size() == 0) {
    	//s.close();
        return null;
      }
      //s.close();
      return l.get(0);
    }
    
    @Transactional
    public void updateModel(Object model)
    {
     // Session s = this.sessionFactory.openSession();
      Session s = this.sessionFactory.getCurrentSession();
      //Transaction t = s.beginTransaction();
      s.update(model);
      //t.commit();
      //s.close();
    }
    
    @Transactional
    public List<Object[]> executeNativeSql(String sql)
    {
     // Session s = this.sessionFactory.openSession();
      Session s = this.sessionFactory.getCurrentSession();
      //s.beginTransaction();
      SQLQuery q = s.createSQLQuery(sql);
      List<Object[]> result = q.list();
      //s.getTransaction().commit();
      //s.close();
      return result;
    }
    
    @Transactional
    public void executeNativeSqlUpdate(String sql)
    {
     // Session s = this.sessionFactory.openSession();
    	 Session s = this.sessionFactory.getCurrentSession();
    //	 s.beginTransaction();
      SQLQuery q = s.createSQLQuery(sql);
      q.executeUpdate();
    //  s.getTransaction().commit();
    //  s.close();
    }
    
    @Transactional
    public List<Map> executeNativeSqlReturnMap(String sql)
    {
      //Session s = this.sessionFactory.openSession();
      Session s = this.sessionFactory.getCurrentSession();
      //s.beginTransaction();
      SQLQuery q = (SQLQuery)s.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
      List<Map> result = q.list();
      //s.getTransaction().commit();
      
      for (int i = 0; i < result.size(); i++) {
			String icon_url = (String) ((Map) result.get(i)).get("icon_url");
			if (!StringUtils.isBlank(icon_url))
			{
				((Map) result.get(i)).put("icon_url", Util.static_res_url + icon_url);
			}
			
			String res_url = (String) ((Map) result.get(i)).get("res_url");
			if (!StringUtils.isBlank(res_url))
			{
				((Map) result.get(i)).put("res_url", Util.static_res_url + res_url);
			}
		}

      //s.close();
      return result;
    }
    
    @Transactional
    public List executeNativeSqlReturnEntity(String sql, Class arg0)
    {
    	Session s = this.sessionFactory.getCurrentSession();
        SQLQuery q = s.createSQLQuery(sql).addEntity(arg0);
        List result = q.list();
        return result;
    }

  }



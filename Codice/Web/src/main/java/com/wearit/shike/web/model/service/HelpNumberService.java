package com.wearit.shike.web.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wearit.shike.web.model.dao.helpnumber.HelpNumberDao;
import com.wearit.shike.web.model.dao.helpnumber.HelpNumberDaoImpl;
import com.wearit.shike.web.model.user.HelpNumber;

public class HelpNumberService {

	private HelpNumberDao helpNumberDao;

	public HelpNumberService() {
		setHelpNumberDao();
	}

	@Autowired
	public void setHelpNumberDao() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Dao-Beans.xml");
		helpNumberDao = (HelpNumberDaoImpl) context.getBean("HelpNumberDaoImpl");
		((ClassPathXmlApplicationContext) context).close();
	}

	public List<HelpNumber> getAll(int idUser) {
		return helpNumberDao.getAll(idUser);
	}

	public HelpNumber get(int idUser, String number) {
		return helpNumberDao.get(idUser, number);
	}

	public void add(int idUser, HelpNumber number) {
		helpNumberDao.add(idUser, number);
	}

	public void delete(int id, String number) {
		helpNumberDao.delete(id, number);
	}

	public void update(int id, String number, HelpNumber newNumber) {
		helpNumberDao.update(id, number, newNumber);
	}

}

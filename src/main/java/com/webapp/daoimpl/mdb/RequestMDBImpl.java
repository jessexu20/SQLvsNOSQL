package com.webapp.daoimpl.mdb;

import org.springframework.stereotype.Component;

import com.webapp.dao.RequestDao;
import com.webapp.model.House;
import com.webapp.model.Request;
import com.webapp.model.User;
@Component
public class RequestMDBImpl extends BaseMDBImpl<Request>  implements RequestDao{


}

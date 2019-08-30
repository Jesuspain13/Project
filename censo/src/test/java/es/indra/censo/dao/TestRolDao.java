package es.indra.censo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import es.indra.censo.model.Rol;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRolDao {
	

		
	@Autowired(required = true)
	private IRolDao rolDao;
	
	
		
	private Rol rolParaTestear;
	
	@Before
	public void before() {


		List<Rol> roles = (List<Rol>) rolDao.findAll();
		rolParaTestear = roles.get(0);
		
	}
	
	
	@Test
	public void TestRolfindByAuthority(){
		
		Rol rolEncontrado = rolDao.findByAuthority(rolParaTestear.getAuthority());
		assertEquals(rolParaTestear.getAuthority(), rolEncontrado.getAuthority());
		
	}
	
	@Test
	public void TestRolfindRolesNotEqualsToOtherRoles (){
		
		List<Rol> rolesParaTestear = new ArrayList <Rol>(); 
		rolesParaTestear.add(rolParaTestear);
		
		List<Rol> rolesATestear = rolDao.findRolesNotEqualsToOtherRoles(rolesParaTestear);
		
		assertFalse(rolesATestear.contains(rolesParaTestear));
		
		
		
	}
}

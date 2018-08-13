package xyz.baktha.oaas.web.service;

import java.awt.Label;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import xyz.baktha.oaas.data.exception.InvalidUserException;
import xyz.baktha.oaas.data.model.UserDetail;
import xyz.baktha.oaas.data.repo.UserDetailRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	public static User buildUser(UserDetail userModel) {

		if(null == userModel) return null;

		return new User(userModel.getPhoneNo(), userModel.getPassword(), getRights(userModel.getRights()));
	}
	
	protected static Set<String> getRights(Collection<GrantedAuthority> authorities) {
		
		return Optional.ofNullable(authorities).map(coll -> coll.stream().filter(val -> !Objects.isNull(val))
				.map(GrantedAuthority::getAuthority).collect(Collectors.toSet())).orElse(null);
	}
	
	protected static List<GrantedAuthority> getRights(Set<String> rights) {
		
		List<GrantedAuthority> grantedAuthority = new LinkedList<>();
		
		if (null != rights && !rights.isEmpty()) {
			
			for (String r : rights) {
				
				grantedAuthority.add(new SimpleGrantedAuthority(r));
			}
		}
		return grantedAuthority;
	}
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private UserDetailRepo userRepo;	
	
	//@Secured("ROLE_ADMIN")
	public User addUser(User user) {
		
		if(!isValid(user)) throw new InvalidUserException();
		UserDetail modelUser = userRepo.save(buildUser(user));		
		return buildUser(modelUser);
        
    }
	
	private UserDetail buildUser(User user) {
		
		if(null == user) return null;
		
		UserDetail userModel = new UserDetail();
		userModel.setPhoneNo(user.getUsername());
		userModel.setPassword(getEncPwd(user.getPassword()));
		userModel.setDateCreated(new Date());
		userModel.setRights(getRights(user.getAuthorities()));
		
		return userModel;
	}
	private String getEncPwd(String plainTxt){
	
		return passwordEncoder.encode(plainTxt);
	}
	public User getUser(String phoneNo) throws UsernameNotFoundException {
		
		if(StringUtils.isEmpty(phoneNo)) return null;
		
		UserDetail userModel = userRepo.findByPhone(phoneNo);
		return buildUser(userModel);
	}
	
	public boolean isValid(User user){
		
		if(null == user) return false;
		
		if(StringUtils.isEmpty(user.getUsername()) || 7 > user.getUsername().length()) return false;
		
		if(StringUtils.isEmpty(user.getPassword()) || 8 > user.getPassword().length()) return false;
		
		return true;
	}
	
	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		
		User user = getUser(arg0);
		
		if(null != user) return user;
		
		throw new UsernameNotFoundException(arg0 + " Not found or Empty!");
	}
	
	//@Secured("ROLE_ADMIN")
	public User resetPwd(String phone, String pwd){
    	
    	if(null == phone || null == pwd || 8 > pwd.length()) return null;
    	UserDetail userModel = userRepo.findByPhone(phone);
    	if(null == userModel) return null;
    	userModel.setPassword(getEncPwd(pwd));    	
    	return buildUser(userRepo.save(userModel)); 
    }
	
	public List<User> getAll(){
		
		List<UserDetail> users = userRepo.findAll();
		
		if(null == users || 0 == users.size()) return null;
		
		List<User> newUsers = new ArrayList<>();
		for (UserDetail userModel : users) {
		
			newUsers.add(buildUser(userModel));
		}
		return newUsers;
	}

}

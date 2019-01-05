package xyz.baktha.oaas.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import xyz.baktha.oaas.data.domain.ClientDomain;
import xyz.baktha.oaas.data.domain.UserDomain;
import xyz.baktha.oaas.data.exception.InvalidClientException;
import xyz.baktha.oaas.data.exception.InvalidUserException;
import xyz.baktha.oaas.data.repo.UserDetailRepo;
import xyz.baktha.oaas.web.builder.DomainBuilder;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailRepo userRepo;

	// @Secured("ROLE_ADMIN")
	public void addUser(User user) {

		final UserDomain userDomain = Optional.ofNullable(user)
				.map(val -> DomainBuilder.buildUserDomain(val, passwordEncoder))
				.orElseThrow(() -> new InvalidUserException(""));
		userRepo.save(userDomain);
	}


	public List<User> getAll() {

		List<UserDomain> users = userRepo.findAll();

		if (null == users || 0 == users.size())
			return null;

		List<User> newUsers = new ArrayList<>();
		for (UserDomain userModel : users) {

			newUsers.add(DomainBuilder.buildUser(userModel));
		}
		return newUsers;
	}

	public User getUser(String phoneNo) throws UsernameNotFoundException {

		if (StringUtils.isEmpty(phoneNo))
			return null;

		UserDomain userModel = userRepo.findByPhone(phoneNo);
		return DomainBuilder.buildUser(userModel);
	}
	
	public long deleteUser(String phoneNo) throws UsernameNotFoundException {
		
		return userRepo.deleteByPhone(phoneNo);
	}


	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {

		User user = getUser(arg0);

		if (null != user)
			return user;

		throw new UsernameNotFoundException(arg0 + " Not found or Empty!");
	}

	// @Secured("ROLE_ADMIN")
	public void resetPwd(String phone, String pwd) {

		if (null == phone || null == pwd || 8 > pwd.length())
			throw new InvalidUserException("Invalid User ID or password :" + phone);
		
		UserDomain userModel = userRepo.findByPhone(phone);
		if (null == userModel)
			throw new InvalidUserException("User ID not found :" + phone);
		
		userModel.setPassword(passwordEncoder.encode(pwd));
		userRepo.save(userModel);
	}

}

/**
 * 
 */
package xyz.baktha.oaas.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.baktha.oaas.data.exception.InvalidClientException;
import xyz.baktha.oaas.data.exception.InvalidUserException;
import xyz.baktha.oaas.web.model.ClientModel;
import xyz.baktha.oaas.web.model.UserModel;
import xyz.baktha.oaas.web.service.AdminService;
import xyz.baktha.oaas.web.util.ValidationUtil;

/**
 * @author power-team
 *
 */
@RestController
public class AdminController {

	@Autowired
	private ValidationUtil validationUtil;

	@Autowired
	private AdminService adminService;

	@PreAuthorize("hasAuthority('ROLE_NIRVAGI')")
	@GetMapping("/users")
	public List<UserModel> getUsers() {

		return adminService.getAllUser();
	}

	@PreAuthorize("hasAuthority('ROLE_NIRVAGI')")
	@GetMapping("/clients")
	public List<ClientModel> getClients() {

		return adminService.getClients();
	}

	@PreAuthorize("hasAuthority('ROLE_NIRVAGI')")
	@PostMapping("/user")
	public void adduser(UserModel form) {

//		TODO: test @valid behavior

		final List<String> errs = isValidUser(form);
		if (0 < errs.size()) {

			throw new InvalidUserException(toStringByComma(errs));
		}
		adminService.addUser(form);
	}

	private String toStringByComma(List<String> errs) {
		return "[ " + StringUtils.collectionToDelimitedString(errs, ",") + " ]";
	}

	@PreAuthorize("hasAuthority('ROLE_NIRVAGI')")
	@PostMapping("/client")
	public void addClient(ClientModel form) {

		final List<String> errs = isValidClient(form);
		if (0 < errs.size()) {

			throw new InvalidClientException(toStringByComma(errs));
		}
		adminService.addClient(form);
	}

	protected List<String> isValidUser(UserModel form) {

		List<String> errMsg = new ArrayList<String>();

		if (!validationUtil.isValidInput("userName", form.getUname(), "AccountName", 8, 20, false)) {

			errMsg.add("Invalid Username");
		}
		if (!validationUtil.isValidInput("Password", form.getPwd(), "Password", 8, 20, false)) {

			errMsg.add("Invalid Password");
		}
		return errMsg;
	}

	protected List<String> isValidClient(ClientModel form) {

		List<String> errMsg = new ArrayList<String>();

		if (!validationUtil.isValidInput("resourceId", form.getResourceId(), "AccountName",8, 20, false)) {

			errMsg.add("Invalid resourceId");
		}
		if (!validationUtil.isValidInput("clientId", form.getClientId(), "AccountName",8, 20, false)) {

			errMsg.add("Invalid ClientId");
		}
		if (!validationUtil.isValidInput("clientSec", form.getClientSec(), "Password",8,15, false)) {

			errMsg.add("Invalid ClientSec");
		}
		
		if (!validationUtil.isValidInput("validity", form.getValidity(), "Number5",1, 5, false)) {

			errMsg.add("Invalid validity");
		}

		if (!validationUtil.isValidInput("dirUrl", form.getDirUrl(), "URL",8, 200, true)) {

			errMsg.add("Invalid dirUrl");
		}

		if (CollectionUtils.isEmpty(form.getGrants())) {

			errMsg.add("Invalid Grants");
		}

		if (CollectionUtils.isEmpty(form.getScopes())) {

			errMsg.add("Invalid Scopes");
		}
		return errMsg;
	}

}

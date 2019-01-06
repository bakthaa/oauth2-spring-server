/**
 * 
 */
package xyz.baktha.oaas.web.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author power-team
 *
 */
public class ValidationUtilTest {

	ValidationUtil validationUtil = new ValidationUtil();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link xyz.baktha.oaas.web.util.ValidationUtil#isValidInput(java.lang.String, java.lang.String, java.lang.String, int, int, boolean)}.
	 */
	@Test
	public void testIsValidInput() {

		assertTrue(validationUtil.isValidInput("userName", "werl1234", "AccountName", 8, 20, false));
		assertTrue(validationUtil.isValidInput("userName", "12345678901234567890", "AccountName", 8, 20, false));
		
		assertFalse(validationUtil.isValidInput("userName", "123456789012345678901", "AccountName", 8, 20, false));
		assertFalse(validationUtil.isValidInput("userName", "wer1234", "AccountName", 8, 20, false));
		assertFalse(validationUtil.isValidInput("userName", "", "AccountName", 8, 20, false));
		
		assertTrue(validationUtil.isValidInput("userName", "", "AccountName", 8, 20, true));
		assertTrue(validationUtil.isValidInput("userName", null, "AccountName", 8, 20, true));

	}

}

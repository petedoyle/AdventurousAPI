package io.adventurous.android.api.exceptions;

/**
 * Used to specify that the API returned a failed status.
 * @author Pete
 *
 */
public class ApiFailureStatusException extends ApiException {
	private static final long serialVersionUID = 1L;

	public ApiFailureStatusException(String msg) {
		super(msg);
	}
}

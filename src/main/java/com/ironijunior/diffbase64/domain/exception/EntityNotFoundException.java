package com.ironijunior.diffbase64.domain.exception;

/**
 * Class that represents an exception for the cases where the
 * application could not find a diff for the identifier.
 *
 * @author Ironi Junior Medina
 */
public class EntityNotFoundException extends RuntimeException {

    private static final String MSG = "There is no data with the identifier %s.";

    /**
     * Constructor that receives the diff identification.
     *
     * @param id diff identification
     */
    public EntityNotFoundException(String id) {
        super(String.format(MSG, id));
    }

}

package com.ironijunior.diffbase64.domain.exception;

import com.ironijunior.diffbase64.domain.enumerator.DiffSide;

/**
 * Class that represents an exception for the cases where
 * the informed side already have information.
 *
 * @author Ironi Junior Medina
 */
public class SideAlreadyFilledException extends RuntimeException {

	private static final long serialVersionUID = -6317606836146142841L;
	
	private static final String MSG = "The side %s is already filled.";

    /**
     * Constructor that receives the side.
     *
     * @param side side that already have data.
     */
    public SideAlreadyFilledException(DiffSide side) {
        super(String.format(MSG, side.name()));
    }
}

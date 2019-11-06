package com.ironijunior.diffbase64.service;

import com.ironijunior.diffbase64.domain.DifferedData;

/**
 * Service class responsible for doing the diff process between
 * the left and right values.
 */
public interface DiffProcessorService {

    /**
     * Process and saves the diff between both sides of the object {@code data}.
     *
     * - If the objects are equal it saves the status as "Objects are equal".
     * - If the objects have different sizes it saves the status as "Objects are different in size".
     * - If the objects have the same size but are not equal, it saves the status "Objects are different"
     * and also saves the list of offsets where are the differences.
     *
     * @param data DifferedData object to be compared
     * @return The object saved.
     */
    DifferedData diffSides(DifferedData data);

}

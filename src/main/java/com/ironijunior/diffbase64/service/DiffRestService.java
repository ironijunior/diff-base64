package com.ironijunior.diffbase64.service;

import com.ironijunior.diffbase64.domain.DifferedData;

/***
 * This class implements the business rules to serve the REST api.
 *
 * @author Ironi Junior Medina
 */
public interface DiffRestService {

    /***
     * Converts an byte array to String.
     * If byte array is null, or is not a valid base64 string,
     * it throws an {@link IllegalArgumentException}.
     * If byte array is empty is converts to an empty String.
     *
     * @param arr byte array
     * @return converted String
     */
    String convertByteArrayToString(byte[] arr);

    /**
     * Saves the data on the left side. It means that this is the oldest
     * value to be compared.
     *
     * @param id diff identification
     * @param data data to be saved on the left side
     * @return returns {@code true} if it was successfully saved.
     */
    boolean saveLeft(String id, String data);

    /**
     * Saves the data on the right side. It means that this is the newest
     * value to be compared.
     *
     * @param id differ id
     * @param data data to be saved on the right side
     * @return returns {@code true} if it was successfully saved.
     */
    boolean saveRight(String id, String data);

    /***
     * Obtains the DifferedData of the passed id.
     * @param id diff identification
     * @return DifferedData related to the passed id.
     */
    DifferedData getById(String id);
}

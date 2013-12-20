/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2013 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
package org.apache.oltu.jose.jws.signature.impl;

import org.apache.oltu.jose.jws.signature.SymmetricKey;

/**
 * Symmetric key implementation used for both <i>sign</i> and <i>verify</i>
 * operations.
 */
public class SymmetricKeyImpl implements SymmetricKey {

    private byte[] key;

    public SymmetricKeyImpl(byte[] key) {
        this.key = key;
    }

    public byte[] getKey() {
        return key;
    }

}

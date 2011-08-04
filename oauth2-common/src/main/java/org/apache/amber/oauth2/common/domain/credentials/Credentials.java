/**
 * Copyright 2011 Newcastle University. All rights reserved.
 * Maciej Machulak, Lukasz Moren, Aad van Moorsel
 *
 * http://research.ncl.ac.uk/smart/
 */

package org.apache.amber.oauth2.common.domain.credentials;

import java.lang.Long;import java.lang.String; /**
 * @author Maciej Machulak (m.p.machulak@ncl.ac.uk)
 * @author Lukasz Moren (lukasz.moren@ncl.ac.uk)
 * @author Aad van Moorsel (aad.vanmoorsel@ncl.ac.uk)
 */
public interface Credentials {

    String getClientId();

    String getClientSecret();

    Long getIssuedAt();

    Long getExpiresIn();
}

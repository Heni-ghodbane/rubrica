/*
 * Copyright 2009-2018 Rubrica
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.rubrica.certificate.ec.securitydata.old;

import static io.rubrica.certificate.ec.securitydata.old.CertificadoSecurityDataOld.OID_CEDULA_PASAPORTE;
import static io.rubrica.certificate.ec.securitydata.old.CertificadoSecurityDataOld.OID_TIPO_FUNCIONARIO_PUBLICO;
import static io.rubrica.certificate.ec.securitydata.old.CertificadoSecurityDataOld.OID_TIPO_MIEMBRO_EMPRESA;
import static io.rubrica.certificate.ec.securitydata.old.CertificadoSecurityDataOld.OID_TIPO_PERSONA_JURIDICA_EMPRESA;
import static io.rubrica.certificate.ec.securitydata.old.CertificadoSecurityDataOld.OID_TIPO_PERSONA_NATURAL;
import static io.rubrica.certificate.ec.securitydata.old.CertificadoSecurityDataOld.OID_TIPO_REPRESENTANTE_LEGAL;
import static io.rubrica.util.BouncyCastleUtils.certificateHasPolicy;

import java.security.cert.X509Certificate;

/**
 * Permite construir certificados tipo CertificadoSecurityData a partir de
 * certificados X509Certificate.
 *
 * @author Ricardo Arguello <ricardo.arguello@soportelibre.com>
 */
public class CertificadoSecurityDataOldFactory {

	public static boolean esCertificadoDeSecurityDataOld(X509Certificate certificado) {
		byte[] valor = certificado.getExtensionValue(OID_CEDULA_PASAPORTE);
		return (valor != null);
	}

	public static CertificadoSecurityDataOld construir(X509Certificate certificado) {
		if (certificateHasPolicy(certificado, OID_TIPO_PERSONA_NATURAL)) {
			return new CertificadoPersonaNaturalSecurityDataOld(certificado);
		} else if (certificateHasPolicy(certificado, OID_TIPO_PERSONA_JURIDICA_EMPRESA)) {
			return new CertificadoPersonaJuridicaSecurityDataOld(certificado);
		} else if (certificateHasPolicy(certificado, OID_TIPO_REPRESENTANTE_LEGAL)) {
			return new CertificadoRepresentanteLegalSecurityDataOld(certificado);
		} else if (certificateHasPolicy(certificado, OID_TIPO_MIEMBRO_EMPRESA)) {
			return new CertificadoMiembroEmpresaSecurityDataOld(certificado);
		} else if (certificateHasPolicy(certificado, OID_TIPO_FUNCIONARIO_PUBLICO)) {
			return new CertificadoFuncionarioPublicoSecurityDataOld(certificado);
		} else {
			throw new RuntimeException("Tipo Certificado de SecurityData desconocido!");
		}
	}
}
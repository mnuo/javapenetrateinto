/**
 * Vire.java created at 2016年8月9日 下午3:58:16
 */
package com.mnuocom.largedistributedweb.chapter3.digitalsignature.demo;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author saxon
 */
public class VerifySignature {

	public void run1() {
		try {
			String pubkeyvalue = "30819f300d06092a864886f70d010101050003818d003081890281810093dbe94b47911b429ae9cbb7181651492a29532a74caef2f15385334d168d75d236b4752ccf24d29ed7f3fa5d52dc18d106f8f1d7fcafac546f9de5bb96633dc3e7b92cf527221aa736b97669e8a8389587cfe05aeaaad15f3f2409fea47ec00c1b81f6036c4c71bdba66230f67114a1e29ee29c5bb6d3f9b5d3171ad0b11fc90203010001";// 这是GenerateKeyPair输出的公钥编码
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(hexStrToBytes(pubkeyvalue));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);

			String info = "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060519";
			byte[] signed = hexStrToBytes("3e5739184a60196ae0a74730489ef511c86f6556ef1a25dbddca96c860d27074f8942bf5e551ba75c97b4f0286359a3133cadaef746c33b5b97472d94373a789116deb6aeff2d9f428b5264f0e83c4a8c64ec750c086143edc9f9c4ac6eef7b338a46fa07801b4bcf27cdc0cab32bb11fca5f993c644dd071de98f8072590937");// 这是SignatureData输出的数字签名
			java.security.Signature signetcheck = java.security.Signature.getInstance("MD5withRSA");
			signetcheck.initVerify(pubKey);
			signetcheck.update(info.getBytes());
			if (signetcheck.verify(signed)) {
				System.out.println("info=" + info);
				System.out.println("签名正常");
			} else
				System.out.println("非签名正常");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Transform the specified byte into a Hex String form.
	 */
	public static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	/**
	 * Transform the specified Hex String into a byte array.
	 */
	public static final byte[] hexStrToBytes(String s) {
		byte[] bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}

		return bytes;
	}

	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	
	public static void main(String[] args) {
		VerifySignature entity = new VerifySignature();
		entity.run1();
	}
	
}

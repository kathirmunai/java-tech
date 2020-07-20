package com.kathirmunai.cms.java;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.util.io.Streams;

import lombok.extern.slf4j.Slf4j;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.callbacks.KeyringConfigCallbacks;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfig;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfigs;

@Slf4j
public class OpenPGP {
	static String normalFile="/Users/sathiya/Documents/kathirmunai.csv";
	static String encryptedFile="/Users/sathiya/Documents/kathirmunai.csv.gpg";
	static String decryptedFile="/Users/sathiya/Documents/kathirmunai-decrypted.csv";
	static String pubKey="pubKey";//Provide valid public key
	static String pvtKey="pvtKey";//Provide valid private key

	public static void main(String[] args) throws SignatureException, NoSuchAlgorithmException, NoSuchProviderException, IOException, PGPException {  
		encryptFile(normalFile, encryptedFile, pubKey, pvtKey);
		decryptFile(encryptedFile, decryptedFile, pubKey, pvtKey);
		log.info("Done successfully");
	}

	private static void encryptFile(String inputFilePath,String encryptedFilePath, String pubKey, String pvtKey) throws IOException, PGPException,
	SignatureException, NoSuchAlgorithmException, NoSuchProviderException {
		InputStream pubStream = new ByteArrayInputStream(pubKey.getBytes());
		InputStream secStream = new ByteArrayInputStream(pvtKey.getBytes());
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		@SuppressWarnings("deprecation")
		final KeyringConfig keyringConfig = 
		KeyringConfigs.withKeyRingsFromStreams(pubStream, secStream, KeyringConfigCallbacks.withPassword(""));

		try (
				final FileOutputStream fileOutput = new FileOutputStream(encryptedFilePath);
				final BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOutput);

				final OutputStream outputStream = BouncyGPG
						.encryptToStream()
						.withConfig(keyringConfig)
						.withStrongAlgorithms()
						.toRecipient("kathirmunai@kathirmunai.com")
						.andSignWith("kathirmunai@kathirmunai.com")
						.binaryOutput()
						.andWriteTo(bufferedOut);
				final FileInputStream is = new FileInputStream(inputFilePath)
				) {
			Streams.pipeAll(is, outputStream);
		}
	}

	private static void decryptFile(String encFilePath,String destFilePath, String pubKey, String pvtKey) throws IOException, PGPException, NoSuchProviderException {
		InputStream pubStream = new ByteArrayInputStream(pubKey.getBytes());
		InputStream secStream = new ByteArrayInputStream(pvtKey.getBytes());
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		@SuppressWarnings("deprecation")
		final KeyringConfig keyringConfig = 
		KeyringConfigs.withKeyRingsFromStreams(pubStream, secStream, KeyringConfigCallbacks.withPassword(""));

		try (
				final FileInputStream cipherTextStream = new FileInputStream(encFilePath);
				final FileOutputStream fileOutput = new FileOutputStream(destFilePath);
				final BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOutput);

				final InputStream plaintextStream = BouncyGPG
						.decryptAndVerifyStream()
						.withConfig(keyringConfig)
						.andIgnoreSignatures()
						.fromEncryptedInputStream(cipherTextStream)
				) {
			Streams.pipeAll(plaintextStream, bufferedOut);
		}
	}

}

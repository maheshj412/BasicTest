package test1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import com.ximpleware.AutoPilot;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XMLModifier;

public class test {

	public static void normalizePathName(String datasetXmlFragment) {
		try {
			VTDGen vg = new VTDGen();
			XMLModifier xm = new XMLModifier();
			vg.setDoc(datasetXmlFragment.getBytes(StandardCharsets.UTF_8));
			vg.parse(false);
			VTDNav vn = vg.getNav();
			xm.bind(vn);
			AutoPilot ap = new AutoPilot(vn);
			// ap.declareXPathNameSpace("ce", "*");
			ap.selectXPath("//simple-head/*");
			while (ap.evalXPath() != -1) {
				vn.getXPathStringVal();
				int index = vn.getText();
				String nodeName = vn.toString(vn.getCurrentIndex());
				if (!nodeName.equalsIgnoreCase("item-info")) {
					ap.selectElement(nodeName);
					while (ap.evalXPath() != -1) {
						System.out.println(vn.toString(vn.getCurrentIndex()));
					}
					System.out.println(vn.toString(vn.getCurrentIndex()));
					try {
						String p = vn.toNormalizedString(index);
						String newPath = p.substring(p.indexOf("/") + 1);
						xm.updateToken(index, newPath);
					} catch (Exception e) {
						continue;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		// try {

		String str = FileUtils.readFileToString(new File("main.xml"), "UTF-8");

		normalizePathName(str);

		/*
		 * VTDGen vg = new VTDGen(); if (vg.parseFile("main.xml", false)) {
		 * VTDNav vn = vg.getNav(); AutoPilot ap = new AutoPilot(vn);
		 * ap.declareXPathNameSpace("ce", "*"); ap.selectXPath("//item-info");
		 * 
		 * while (ap.evalXPath() != -1) { if (vn.matchElement("pii")) {
		 * System.out.println(vn.toNormalizedString(vn.getText())); }
		 * 
		 * System.out.println(vn.toNormalizedString(vn.getText())); }
		 * 
		 * int i; while (ap.evalXPath() != -1) {
		 * 
		 * vn.toNormalizedString(2);
		 * 
		 * System.out.println(vn.toNormalizedString(16));
		 * System.out.println(vn.toNormalizedString(17));
		 * System.out.println(vn.toNormalizedString(18));
		 * 
		 * System.out.println("student name ====>" + vn.toString(1)); } } }
		 * catch (Exception e) { System.out.println("exception occurred ==>" +
		 * e); } }
		 */
	}
}
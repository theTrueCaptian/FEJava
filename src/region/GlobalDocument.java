package region;

import java.util.ArrayList;

import utilsFE.MiscUtil;
/********************************************************************************
Global Document Object.
Info about the current document is stored in here
********************************************************************************/
public class GlobalDocument {
	private String document;
	private ArrayList<String> documentLines;

	public GlobalDocument(String document) {
		this.document = document;
		this.documentLines = MiscUtil.splitAndRetainByNewline(this.document);
	}

	public String getDocument() {
		return this.document;
	}

	public ArrayList<String> getDocumentByLines() {
		return this.documentLines;
	}

	// Returns the line previous to that line in the this.documentLines[]
	public String getPredAt(String x) {
		int pos = getLineNumber(this.document, x);
		return this.documentLines.get(pos - 1);
	}

	// Returns the line succeeding to that line in the this.documentLines[]
	public String getSuccAt(String x) {
		int pos = getLineNumber(this.document, x);
		return this.documentLines.get(pos + 1);
	}

	// Get the line number of region with respect to the document (String)
	private int getLineNumber(String inDoc, String region) {
		String[] lines = inDoc.split("\n");

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (line.indexOf(region) != -1) {
				return i;
			}
		}
		return -1;
	}

	public String toString() {
		return "GlobalDocument(" + this.document + ", " + ")";
	};

	/*
	 * public void obj () { return {'document':this.document} };
	 */

}

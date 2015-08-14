package model;

public class QrCode {
	private String code;

	public QrCode(int number) {
		setAdminQrCode(number);
	}

	public QrCode(int number, String naam, String voornaam) {
		setChildQrCode(number, naam, voornaam);
	}

	public void setAdminQrCode(int number) {
		if (number == -1) {
			this.code = Integer.toString(number);
		}
	}

	public void setChildQrCode(int number, String naam, String voornaam) {
		if (number > 0 && naam != null && voornaam != null) {
			this.code = number + "/" + naam + "/" + voornaam;
		}
	}

	public String getCode() {
		return code;
	}

	@Override
	public boolean equals(Object object) {
		boolean gelijk = false;
		if (object != null && object instanceof QrCode) {
			QrCode qrCode = (QrCode) (object);
			if (this.getCode().toUpperCase().equals(qrCode.getCode().toUpperCase())) {
				gelijk = true;
			}
		}
		return gelijk;
	}
}

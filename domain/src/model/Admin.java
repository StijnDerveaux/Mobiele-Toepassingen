package model;

public class Admin implements User{
	
	private QrCode qrcode;
	private int number=-1;
	
	
	public Admin(){
		
		setQrCode(number);
	}
	
	

	public int getNumber() {
		return number;
	}
/*
	private void setNumber(int number) {
		
		this.number = number;
	}
*/
	
	public void setQrCode(int number) {
		// TODO Auto-generated method stub
		qrcode=new QrCode(number);
	}

	@Override
	public QrCode getQrCode() {
		// TODO Auto-generated method stub
		return qrcode;
	}
	
	 @Override
	    public boolean equals(Object object) {
	        boolean gelijk = false;
	        if (object != null && object instanceof Admin) {
	        	Admin aan = (Admin) (object);
	            if (this.getQrCode().equals(aan.getQrCode()))  {
	                gelijk = true;
	            }
	        }
	        return gelijk;
	    }



	@Override
	public void setNumber(int number) {
		// TODO Auto-generated method stub
		this.number = number;
		
	}

}

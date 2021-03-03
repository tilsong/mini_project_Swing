
package Information;

import java.io.Serializable;

import javax.swing.JButton;

//현재 상태를 유지하기 위한
public class ParkCarInfo implements Serializable { //각 주차 차량의 정보를 담기 위한 클래스 작성 , 파일에 저장해야하기 떄문에 직렬화 시켜놓는다.

	private int parkPlaceNum; //주차공간의 인덱스를 저장
	private String carNum; //차량번호를 저장
	private String carInTime; //carIntime.getTimeInMillis()를 이용해 현재 시간을 초로 받아온다 
	private int parkingCode;
	
	public ParkCarInfo(int parkPlaceNum, String carNum, String carInTime, int parkingCode) {
		//setter()는 생성과 동시에 진행하게 하고 , getter()만 따로 메소드로 만들어 다른 패키지의 클래스에서 접근하는 것을 허용한다.
		this.parkPlaceNum = parkPlaceNum;
		this.carNum = carNum;
		this.carInTime = carInTime;
		this.parkingCode = parkingCode;
	}

	public int getparkPlaceNum()
	{
		return parkPlaceNum;
	}

	public int getParkingCode() {
		return parkingCode;
	}
	
	public String getcarNum() {
		return carNum;
	}
	public String getCarInTime() {
		return carInTime;
	}
}

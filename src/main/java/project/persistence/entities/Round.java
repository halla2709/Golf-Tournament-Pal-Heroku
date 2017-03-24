package project.persistence.entities;

import javax.persistence.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private int h1;

	private int h2;
	private int h3;
	private int h4;
	private int h5;
	private int h6;
	private int h7;
	private int h8;
	private int h9;
	private int h10;
	private int h11;
	private int h12;
	private int h13;
	private int h14;
	private int h15;
	private int h16;
	private int h17;
	private int h18;
	private int total;
	
	@Transient()
	private int[] myScores = {h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13, h14, h15, h16, h17, h18};
	
	public Round() {
		setScore();
	}
	
	public int getTotal() {
		total = 0;
		for(int i = 0; i < 18; i++) {
			total += myScores[i];
		}
		return total;
	}
	
	public void setScore() {
		myScores[0] = h1;
		myScores[1] = h2;
		myScores[2] = h3;
		myScores[3] = h4;
		myScores[4] = h5;
		myScores[5] = h6;
		myScores[6] = h7;
		myScores[7] = h8;
		myScores[8] = h9;
		myScores[9] = h10;
		myScores[10] = h11;
		myScores[11] = h12;
		myScores[12] = h13;
		myScores[13] = h14;
		myScores[14] = h15;
		myScores[15] = h16;
		myScores[16] = h17;
		myScores[17] = h18;
	}
	
	public void setScore(int[] scores) {
		myScores = scores;
		h1 = scores[0];
		h2 = scores[1];
		h3 = scores[2];
		h4 = scores[3];
		h5 = scores[4];
		h6 = scores[5];
		h7 = scores[6];
		h8 = scores[7];
		h9 = scores[8];
		h10 = scores[9];
		h11 = scores[10];
		h12 = scores[11];
		h13 = scores[12];
		h14 = scores[13];
		h15 = scores[14];
		h16 = scores[15];
		h17 = scores[16];
		h18 = scores[17];
		total = getTotal();
	}
	
	public int getScore(int i) {
		return myScores[i];
	}
	
	public int[] getMyScores() {
		return myScores;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getH1() {
		setScore();
		return h1;
	}

	public void setH1(int h1) {
		this.h1 = h1;
		setScore();
	}

	public int getH2() {
		setScore();
		return h2;
	}

	public void setH2(int h2) {
		this.h2 = h2;
		setScore();
	}

	public int getH3() {
		setScore();
		return h3;
	}

	public void setH3(int h3) {
		this.h3 = h3;
		setScore();
	}

	public int getH4() {
		setScore();
		return h4;
	}

	public void setH4(int h4) {
		this.h4 = h4;
		setScore();
	}

	public int getH5() {
		setScore();
		return h5;
	}

	public void setH5(int h5) {
		this.h5 = h5;
		setScore();
	}

	public int getH6() {
		setScore();
		return h6;
	}

	public void setH6(int h6) {
		this.h6 = h6;
		setScore();
	}

	public int getH7() {
		setScore();
		return h7;
	}

	public void setH7(int h7) {
		this.h7 = h7;
		setScore();
	}

	public int getH8() {
		setScore();
		return h8;
	}

	public void setH8(int h8) {
		this.h8 = h8;
		setScore();
	}

	public int getH9() {
		setScore();
		return h9;
	}

	public void setH9(int h9) {
		this.h9 = h9;
		setScore();
	}

	public int getH10() {
		setScore();
		return h10;
	}

	public void setH10(int h10) {
		this.h10 = h10;
		setScore();
	}

	public int getH11() {
		setScore();
		return h11;
	}

	public void setH11(int h11) {
		this.h11 = h11;
		setScore();
	}

	public int getH12() {
		setScore();
		return h12;
	}

	public void setH12(int h12) {
		this.h12 = h12;
		setScore();
	}

	public int getH13() {
		setScore();
		return h13;
	}

	public void setH13(int h13) {
		this.h13 = h13;
		setScore();
	}

	public int getH14() {
		return h14;
	}

	public void setH14(int h14) {
		this.h14 = h14;
		setScore();
	}

	public int getH15() {
		setScore();
		return h15;
	}

	public void setH15(int h15) {
		this.h15 = h15;
		setScore();
	}

	public int getH16() {
		setScore();
		return h16;
	}

	public void setH16(int h16) {
		this.h16 = h16;
		setScore();
	}

	public int getH17() {
		setScore();
		return h17;
	}

	public void setH17(int h17) {
		this.h17 = h17;
		setScore();
	}

	public int getH18() {
		setScore();
		return h18;
	}

	public void setH18(int h18) {
		this.h18 = h18;
		setScore();
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setMyScores(int[] myScores) {
		this.myScores = myScores;
	}
	
}

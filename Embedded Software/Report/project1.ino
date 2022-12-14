/*
  -------------------------------------------------------------------------------------------------------------------
  근전도 센서를 기용한 이유 :
  근전도 센서는 바늘을 근육에 찔러넣는 방식보다 정확도면에서는 떨어진다.
  다만. 바늘을 꼽은 상태로 큰 움직임 + 행동을 할 경우 신경을 건드리거나, 근육의 손상이 있을 가능성이 존재하기 때문에
  근전도 센서를 기용하였다.
  생활체육에 관심이 많은 사람으로써, 신체의 활동이나 움직임 운동을 독학할 때 유용한 방법이 없을까? 하는 호기심에서 나온 기획안.
  -------------------------------------------------------------------------------------------------------------------
  <상황 전개 - 1>
  1. 근전도 센서와 연결된 패드를 측정하고 싶은 부위(서로 다른 부위 둘)에 붙인다.
  2. 주동근을 확인하기 위해 운동을 시도하는데, 측정 시 지인이 있으면 모니터링 하기 좋다.
  3. 원하는 부위의 발달을 위해 자세를 바꿔간다.
  <상황 전개 - 2>
  1. 근전도 센서를 동일 부위 다른 부분에 붙인다(ex. 척추기립근)
  2. 아래의 코드를 사용하여 부저가 울리는지 확인한다.
  3. 울리지 않고 자연스럽게 진행이 된다면 원활히 진행 됨을 알 수 있다.
  -------------------------------------------------------------------------------------------------------------------
  조건 1 :
  센서 한 쌍(2개)로 값을 측정하여 값이 50(예상값)보다 크게 차이난다면 부저가 울리도록 구현.
  조건 2 :
  오실로스코프나, led 등을 활용하여 직간접적인 근력 시각자료를 추가하는 것도 고려.
  조건 3 : 
  포텐쇼미터를 사용하여 모드를 변경하게 할 것.
  조건 4 :
  근력의 성장 한계를 측정하기 위한 코드도 마련할 것
  -------------------------------------------------------------------------------------------------------------------
  장점 1 :
  운동 시 가지게 되는 잘못된 습관이나 불규칙한 행동을 고칠 수 있다(나만의 퍼스널 트레이너)
  장점 2 :
  프리웨이트 운동은 주동근이 자세에 따라 다름. 이를 데이터를 통해 객관적으로 수용할 수 있는 지표가 생긴다.
  장점 3 :
  자세 교정시 민감성을 부여하여 긴장감을 유지할 수 있다. 평소에 나쁜 습관이 있다면 응용 사용도 고려할만함
  -------------------------------------------------------------------------------------------------------------------
  추가 사항
  1. 피에조 부저는 + 부분이 9번핀 다른 부분이 gnd 연결.
  2. 포텐셜미터는 중앙이 A0 나머지는 gnd 5v 구분없이 연결.
  -------------------------------------------------------------------------------------------------------------------
*/

#include <Servo.h>

int buzzerPin = 9; // 피에조부조 +를 9번에 연결
int tempo = 200; // 노래의 빠르기를 설정한다.

const int ledLow = 5; // 세기가 약할 때 led
const int ledPeak = 6; // 피크일 때 led

void setup() {

  Serial.begin(9600);

  pinMode(buzzerPin, OUTPUT);

  pinMode(ledLow, OUTPUT); // 평소보다 낮을 때
  pinMode(ledPeak, OUTPUT); // 피크일 때
}

void loop() {

  // 부저 출력 시간에 사용할 변수 설정
  int duration;

  duration = tempo;

  int adcValue = analogRead(A0); // 포텐쇼미터 값을 읽는다.

  int val1 = analogRead(A1); // 근전도 센서 1
  int val2 = analogRead(A2); // 근전도 센서 2


  if (adcValue < 340) {
    // 아무것도 안함. Mode Sleep
  }
  
  else if (adcValue < 680) {

    if (val1 - val2 > 50 || val2 - val1 > 50) { // 50 이상 두 센서의 차이가 날 시 부저음 발생.
      
      Serial.print("<이벤트> 균형모드 1센서의 값은 : ");
      Serial.println(val1);
      Serial.print("<이벤트> 균형모드 2센서의 값은 : ");
      Serial.println(val2);

      // tone 명령어를 통하여 부저 핀으로 사각파를 출력한다
      tone(buzzerPin, 262, 50);
      delay(200);
      
    }

    else {
      
      Serial.print("1. 균형모드 1센서의 값은 : ");
      Serial.println(val1);
      Serial.print("1. 균형모드 2센서의 값은 : ");
      Serial.println(val2);
      delay(200);
      
    }
  }
  
  else {

    if (val1 > 990 ) { // 수정 예정 현재 센서가 없음

      Serial.println("<이벤트> 측정모드 피크값 발생");

      digitalWrite(ledPeak, HIGH);
      tone(buzzerPin, 1046, 50);
      delay(200);
      digitalWrite(ledPeak, LOW);
      
    }

    else if (val1 < 600){ // 수정 예정 현재 센서가 없음
      Serial.print("2. 측정모드 1센서의 값은 : ");
      Serial.println(val1);
      digitalWrite(ledLow, HIGH);
      delay(200);
      digitalWrite(ledLow, LOW);
    }
  }
}

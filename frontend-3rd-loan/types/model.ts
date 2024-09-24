export type Product = {
  loanProductsType: string | null; // loanProductsType이 null이 될 수 있으므로 string | null
  interestRate: number; // 금리는 number 타입
  maxLimit: number; // 최대 한도는 number 타입
  repaymentPeriod: number; // 상환 기간은 number 타입
  loanProductsFeature: string; // 대출 상품 특징은 string 타입
  applicationMethod: string; // 신청 방법은 string 타입
  requiredCreditScore: number; // 필요한 신용 점수는 number 타입
  provider: string; // 제공자는 string 타입
  id: number; // id는 number 타입
  startDate: string; // 시작 날짜는 string으로 표현
  endDate: string; // 끝 날짜도 string으로 표현
  loanProductsTypeName: string; // 대출 상품 유형 이름은 string
};

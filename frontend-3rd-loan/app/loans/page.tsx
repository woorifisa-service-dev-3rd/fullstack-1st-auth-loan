"use client";

//import { useRouter } from "next/navigation";
import React, { useState } from "react";

const Loan = () => {
  // 폼 입력값을 상태로 관리
  const [memberId, setUserId] = useState("");
  const [loanProductId, setProductId] = useState("");
  const [loanAmount, setLoanAmount] = useState("");

  //const router = useRouter();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault(); // 폼 제출 시 페이지 리로드 방지

    // 서버로 보낼 대출 정보
    const loanData = {
      memberId,
      loanProductId,
      loanAmount,
    };

    try {
      // POST 요청 보내기
      const url = process.env.NEXT_PUBLIC_SERVER_API_URL;
      const response = await fetch(`${url}/loans`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loanData), // loanData를 JSON으로 변환하여 전송
      });

      if (response.ok) {
        // 성공적으로 저장되었을 경우 처리
        const data = await response.json();
        alert(`${data.memberName}님 ${data.loanProductTypeName} ${data.loanAmount}원 대출 성공!`);
        console.log(data);
      } else {
        // 에러 처리
        const data = await response.json();
        alert(data.message);
        console.log(data);
      }
    } catch (error) {
      console.error("Error:", error);
      alert("서버에 요청하는 중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="w-1/3">
      <h1 className="text-center text-3xl font-bold">대출 정보 입력</h1>
      <form className="flex flex-col mt-10" onSubmit={handleSubmit}>
        <div className="flex justify-between mb-5">
          <label htmlFor="userId" className="w-1/3 text-center font-bold mr-5">
            사용자 아이디
          </label>
          <input
            id="userId"
            className="w-2/3 border border-black"
            placeholder="사용자 아이디를 입력하세요."
            value={memberId}
            onChange={(e) => setUserId(e.target.value)} // 입력 값 업데이트
            required
          />
        </div>
        <div className="flex justify-between mb-5">
          <label htmlFor="productId" className="w-1/3 text-center font-bold mr-5">
            상품 아이디
          </label>
          <input
            id="productId"
            className="w-2/3 border border-black"
            placeholder="상품 아이디를 입력하세요."
            value={loanProductId}
            onChange={(e) => setProductId(e.target.value)} // 입력 값 업데이트
            required
          />
        </div>
        <div className="flex justify-between mb-5">
          <label htmlFor="loanAmount" className="w-1/3 text-center font-bold mr-5">
            대출 금액
          </label>
          <input
            id="loanAmount"
            className="w-2/3 border border-black"
            placeholder="대출 금액을 입력하세요."
            value={loanAmount}
            onChange={(e) => setLoanAmount(e.target.value)} // 입력 값 업데이트
            required
          />
        </div>

        <button type="submit" className="bg-blue-600 w-1/3 self-center text-white p-3">
          대출하기
        </button>
      </form>
    </div>
  );
};

export default Loan;

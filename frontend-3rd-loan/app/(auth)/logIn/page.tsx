"use client";

import React, { useState } from "react";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogIn = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault(); // 폼 제출 시 페이지 리로드 방지

    // 서버로 보낼 대출 정보
    const logInData = {
      username,
      password,
    };

    try {
      // POST 요청 보내기
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(logInData), // logInData를 JSON으로 변환하여 전송
      });

      if (response.ok) {
        // 성공적으로 저장되었을 경우 처리
        const data = await response.json();
        alert("로그인 성공");
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
    <form className="flex flex-col items-center w-1/2 bg-white border rounded-md p-5">
      <h1 className="text-center text-3xl font-bold mb-5">로그인</h1>
      <input
        placeholder="이메일을 입력하세요."
        type="email"
        className="mb-5 border-2 rounded-md p-2 w-full"
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        placeholder="비밀번호를 입력하세요."
        type="password"
        className="mb-5 border-2 rounded-md p-2 w-full"
        onChange={(e) => setPassword(e.target.value)}
      />
      <button
        className="border rounded-md bg-blue-600 w-20 text-white p-1"
        onClick={() => handleLogIn}
      >
        로그인
      </button>
    </form>
  );
};

export default Login;

import Image from "next/image";
import React from "react";

const Home = () => {
  return (
    <div>
      <Image src="/images/logo.jpeg" alt="로고 이미지" width={400} height={400} />
      <h1 className="text-5xl text-blue-900 font-bold mt-10">우리 FISA 캐피탈</h1>
    </div>
  );
};

export default Home;

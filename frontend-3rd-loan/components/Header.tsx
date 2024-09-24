import Image from "next/image";
import Link from "next/link";
import React from "react";

const menuItems = [
  { id: 1, name: "대출 상품", url: "/products" },
  { id: 2, name: "대출 상품 추천", url: "/recommends" },
  { id: 3, name: "신용 대출", url: "/loans" },
];

const Header = () => {
  return (
    <header className="fixed top-0 left-0 w-screen p-5 bg-blue-600 text-white flex flex-row items-center">
      <Link href="/">
        <div className="flex flex-row text-2xl mr-10">
          <Image
            src="/images/logo.jpeg"
            alt="로고 이미지"
            width={30}
            height={30}
            className="mr-1"
          ></Image>
          우리FISA 캐피탈
        </div>
      </Link>

      <ul className="flex flex-row">
        {menuItems.map((menuItem) => (
          <li className="mr-5" key={menuItem.id}>
            <Link href={menuItem.url}>{menuItem.name}</Link>
          </li>
        ))}
      </ul>
    </header>
  );
};

export default Header;

import { ProductProps } from "@/types/props";
import Image from "next/image";
import Link from "next/link";
import React from "react";

const ProductItem: React.FC<ProductProps> = ({ product }) => {
  return (
    <li>
      <Link href={`/products/${product.id}`}>
        <div className="bg-white border-4 border-blue-500 rounded-md m-5 p-5 flex flex-row">
          <Image src="/images/logo.jpeg" alt="상품 이미지" width={100} height={100}></Image>
          <div className="ml-10">
            <h1 className="text-xl font-bold mb-3">{product.loanProductsTypeName}</h1>
            <dl className="flex flex-row">
              <div className="mr-5 text-center">
                <dt className="font-bold">최저 금리</dt>
                <dd>{product.interestRate}%</dd>
              </div>
              <div className="mr-5 text-center">
                <dt className="font-bold">최대 한도</dt>
                <dd>{product.maxLimit}원</dd>
              </div>
              <div className="mr-5 text-center">
                <dt className="font-bold">신용점수</dt>
                <dd>{product.requiredCreditScore}점</dd>
              </div>
              <div className="mr-5 text-center">
                <dt className="font-bold">상환 기간</dt>
                <dd>{product.repaymentPeriod}개월</dd>
              </div>
              <div className="mr-5 text-center">
                <dt className="font-bold">신청 방법</dt>
                <dd>{product.applicationMethod}</dd>
              </div>
            </dl>
          </div>
        </div>
      </Link>
    </li>
  );
};

export default ProductItem;

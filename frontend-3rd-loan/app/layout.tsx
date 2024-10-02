import type { Metadata } from "next";
import "@/styles/globals.css";
import Header from "@/components/Header";

export const metadata: Metadata = {
  title: "우리 FISA 대출",
  description: "우리 FISA 대출",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="flex flex-col items-center pt-28 bg-sky-200 w-screen h-screen">
        <Header />
        {children}
      </body>
    </html>
  );
}

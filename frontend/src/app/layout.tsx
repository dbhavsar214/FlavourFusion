// components/Layout/index.tsx

import React from 'react';
import { Inter } from 'next/font/google';
import Header from './Components/Layout/Header';
import Footer from './Components/Layout/Footer';
import '../styles/globals.css';

const inter = Inter({ subsets: ['latin'] });

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  return (
      <html lang="en">
      <body className={inter.className}>
      <div className="root-layout">
        <Header />
        <main>{children}</main>
        <Footer />
      </div>
      </body>
      </html>
  );
};

export default Layout;
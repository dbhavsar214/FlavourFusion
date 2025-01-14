import Link from 'next/link'
import React from 'react'
import { FaFacebook, FaTwitter, FaInstagram } from 'react-icons/fa';
import { Spicy_Rice } from 'next/font/google';

const s_rice = Spicy_Rice({ weight : '400', preload : false})

const Footer = () => {
  return (
    <div>
      <footer className="bg-[#14b8a6] text-white py-8">
      <div className="container mx-auto px-4">
        <div className="flex flex-col md:flex-row justify-between items-center">
          <div className="mb-6 md:mb-0">
          <h2 className={`text-2xl font-bold ${s_rice.className}`}>ff</h2>
            <p className="mt-2">Share your favorite recipes with the world.</p>
          </div>
          <div className="flex space-x-4 mb-6 md:mb-0">
            <Link className="hover:text-gray-400" href="/about">
              About
            </Link>
            <Link className="hover:text-gray-400" href="/about">
              Contact
            </Link>
            <Link className="hover:text-gray-400" href="/about">
              Pricacy Policy
            </Link>
          
          </div>
          <div className="flex space-x-4">
            <a href="https://facebook.com" target="_blank" rel="noopener noreferrer" className="hover:text-gray-400">
              <FaFacebook size={24} />
            </a>
            <a href="https://twitter.com" target="_blank" rel="noopener noreferrer" className="hover:text-gray-400">
              <FaTwitter size={24} />
            </a>
            <a href="https://instagram.com" target="_blank" rel="noopener noreferrer" className="hover:text-gray-400">
              <FaInstagram size={24} />
            </a>
          </div>
        </div>
      </div>
    </footer>
    </div>
  )
}

export default Footer

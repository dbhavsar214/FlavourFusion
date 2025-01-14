'use client';
import Link from 'next/link';
import { useState, useEffect, useRef } from 'react';
import { useRouter, usePathname } from 'next/navigation';
import Image from 'next/image';

const Header: React.FC = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [userId, setUserId] = useState<string | null>(null);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const router = useRouter();
  const pathname = usePathname();
  const dropdownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const session = sessionStorage.getItem('session');
    if (session) {
      try {
        const parsedSession = JSON.parse(session);
        const id = parsedSession.userID;

        if (id) {
          setIsLoggedIn(true);
          setUserId(id);
        } else {
          setIsLoggedIn(false);
        }
      } catch (error) {
        console.error('Error parsing session data:', error);
        setIsLoggedIn(false);
      }
    }

    setLoading(false);
  }, []);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setIsDropdownOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const handleSearch = (event: React.FormEvent) => {
    event.preventDefault();
    router.push(`/search?query=${searchQuery}`);
  };

  const handleLogout = () => {
    sessionStorage.removeItem('session');
    setIsLoggedIn(false);
    setUserId(null);
    router.push('/home');
  };

  if (loading) {
    return null; // Render nothing until login status is checked
  }

  return (
      <header className="bg-[#14b8a6] p-4 flex flex-col sm:flex-row items-center justify-between">
        <Link href="/home">
          <Image
              src="/images/Main-Icon.png" // Replace with your actual icon path
              alt="Home Icon"
              width={50}
              height={50}
              className="cursor-pointer"
          />
        </Link>
        {pathname !== '/search' && (
            <form onSubmit={handleSearch} className="flex-grow max-w-md mx-4 w-full sm:w-auto flex">
              <input
                  type="text"
                  placeholder="Search..."
                  className="w-full p-2 rounded-l border border-gray-400"
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
              />
              <button type="submit" className="p-2 rounded-r bg-white border border-gray-400">Search</button>
            </form>
        )}
        <div className="flex items-center space-x-4">
          {!isLoggedIn ? (
              <>
                <Link href="/login" className='text-white'>Login</Link>
                <Link href="/signup" className='text-white'>Signup</Link>
              </>
          ) : (
              <div className="relative" ref={dropdownRef}>
                <div onClick={toggleDropdown} className="cursor-pointer hover:scale-110 transition-transform">
                  <Image
                      src="/images/DefaultProfile.jpg" // Replace with your actual profile icon path
                      alt="Profile Icon"
                      width={40}
                      height={40}
                      className="rounded-full"
                  />
                </div>
                {isDropdownOpen && (
                    <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1">
                      <Link href={`/user/${userId}/profile`} className="block px-4 py-2 text-gray-800 hover:bg-gray-100 hover:underline">
                        My Profile
                      </Link>
                      <Link href={`/user/${userId}/recipes`} className="block px-4 py-2 text-gray-800 hover:bg-gray-100 hover:underline">
                        My Recipes
                      </Link>
                      <button
                          onClick={handleLogout}
                          className="block w-full text-left px-4 py-2 text-gray-800 hover:bg-gray-100 hover:underline"
                      >
                        Sign Out
                      </button>
                    </div>
                )}
              </div>
          )}
        </div>
      </header>
  );
};

export default Header;

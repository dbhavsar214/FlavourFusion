'use client';
import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL

interface IFormInput {
  email: string;
  password: string;
}


const schema = yup.object({
  email: yup.string().email('Invalid email address').required('Email is required'),
  password: yup.string().min(8, 'Password must be at least 8 characters').required('Password is required'),
}).required();

const LoginForm: React.FC = () => {
  const { register, handleSubmit, formState: { errors } } = useForm<IFormInput>({
    resolver: yupResolver(schema),
  });

  const router = useRouter();
  const [errorMessage, setErrorMessage] = useState('');


  const onSubmit: SubmitHandler<IFormInput> = async (data) => {
    try {

      const response = await fetch(`${BASE_URL}/auth/login`, { // Corrected string interpolation
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        const errorData = await response.json();
        setErrorMessage(errorData.message);
        throw new Error('Network response was not ok');
      }

      const result = await response.json();
      sessionStorage.setItem("session", JSON.stringify(result));
      console.log('Success:', result);

      // Redirect to home page and refresh
      router.push('/home');
      setTimeout(() => {
        window.location.href = '/home';
      }, 100);

    } catch (error) {
      console.error('Error:', error);
      setErrorMessage("Invalid credentials");
    }
  };

  return (
      <div className="min-h-screen flex items-center justify-center bg-gray-100 p-4">
        <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-lg">
          <h2 className="text-3xl mb-8 text-center text-teal-600 font-semibold">Login</h2>
          <form onSubmit={handleSubmit(onSubmit)}>
            <div className="mb-6">
              <label className="block text-teal-600 text-lg font-medium mb-2" htmlFor="email">
                Email
              </label>
              <input
                  type="email"
                  id="email"
                  {...register('email')}
                  className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
                      errors.email ? 'border-red-500' : 'border-teal-300'
                  }`}
                  placeholder="Enter your email"
              />
              {errors.email && <p className="text-red-500 text-sm mt-1">{errors.email.message}</p>}
            </div>
            <div className="mb-6">
              <label className="block text-teal-600 text-lg font-medium mb-2" htmlFor="password">
                Password
              </label>
              <input
                  type="password"
                  id="password"
                  {...register('password')}
                  className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
                      errors.password ? 'border-red-500' : 'border-teal-300'
                  }`}
                  placeholder="Enter your password"
              />
              {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>}
            </div>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            <div className="flex items-center justify-between">
              <button
                  type="submit"
                  className="bg-teal-600 hover:bg-teal-700 text-white font-bold py-3 px-6 rounded-lg focus:outline-none focus:shadow-outline transition duration-200"
              >
                Login
              </button>
            </div>
          </form>
        </div>
      </div>
  );
};

export default LoginForm;

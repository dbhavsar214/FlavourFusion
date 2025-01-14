'use client';
import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { BASE_URL } from '@/config/apiConfig'; // Import the base URL
//import Router from 'next/router';
//import Link from 'next/link';
//import { redirect } from "next/navigation";

interface IFormInput {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    confirmPassword: string;
    bio: string;
}

const schema = yup.object({
    firstName: yup.string().required('First name is required'),
    lastName: yup.string().required('Last name is required'),
    email: yup.string().email('Invalid email address').required('Email is required'),
    password: yup.string().min(8, 'Password must be at least 8 characters').required('Password is required'),
    confirmPassword: yup.string()
        .oneOf([yup.ref('password')], 'Passwords must match')
        .required('Confirm password is required'),
    bio: yup.string().required('Bio is required'),
}).required();

const SignUpForm: React.FC = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<IFormInput>({
        resolver: yupResolver(schema),
    });

    const router = useRouter();
    const [errorMessage, setErrorMessage] = useState('');

    const onSubmit: SubmitHandler<IFormInput> = async (data) => {
        console.log('Submitting form data:', data); // Check if this logs the form data
        try {
            const response = await fetch(`${BASE_URL}/auth/signup`, { // Dummy API endpoint
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
            console.log('Success:', result);
            console.log('redirected to login page');
            router.push('/login');
        } catch (error) {
            console.error('Error:', error);
                setErrorMessage("Email already taken!!");
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-white p-6">
            <div className="bg-white p-10 rounded-lg shadow-2xl w-full max-w-2xl">
                <h2 className="text-4xl mb-8 text-center text-teal-600 font-bold">Sign Up</h2>
                <form onSubmit={handleSubmit(onSubmit)}>
                {/* <form onSubmit=> */}
                    <div className="grid grid-cols-2 gap-6 mb-6">
                        <div>
                            <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="firstName">
                                First Name
                            </label>
                            <input
                                type="text"
                                id="firstName"
                                {...register('firstName')}
                                className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.firstName ? 'border-red-500' : 'border-gray-300'
                                    }`}
                                placeholder="Enter your first name"
                            />
                            {errors.firstName && <p className="text-red-500 text-sm mt-1">{errors.firstName.message}</p>}
                        </div>
                        <div>
                            <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="lastName">
                                Last Name
                            </label>
                            <input
                                type="text"
                                id="lastName"
                                {...register('lastName')}
                                className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.lastName ? 'border-red-500' : 'border-gray-300'
                                    }`}
                                placeholder="Enter your last name"
                            />
                            {errors.lastName && <p className="text-red-500 text-sm mt-1">{errors.lastName.message}</p>}
                        </div>
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="email">
                            Email
                        </label>
                        <input
                            type="email"
                            id="email"
                            {...register('email')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.email ? 'border-red-500' : 'border-gray-300'
                                }`}
                            placeholder="Enter your email"
                        />
                        {errors.email && <p className="text-red-500 text-sm mt-1">{errors.email.message}</p>}
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="password">
                            Password
                        </label>
                        <input
                            type="password"
                            id="password"
                            {...register('password')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.password ? 'border-red-500' : 'border-gray-300'
                                }`}
                            placeholder="Enter your password"
                        />
                        {errors.password && <p className="text-red-500 text-sm mt-1">{errors.password.message}</p>}
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="confirmPassword">
                            Confirm Password
                        </label>
                        <input
                            type="password"
                            id="confirmPassword"
                            {...register('confirmPassword')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.confirmPassword ? 'border-red-500' : 'border-gray-300'
                                }`}
                            placeholder="Confirm your password"
                        />
                        {errors.confirmPassword && <p className="text-red-500 text-sm mt-1">{errors.confirmPassword.message}</p>}
                    </div>
                    <div className="mb-6">
                        <label className="block text-gray-600 text-sm font-medium mb-2" htmlFor="bio">
                            Bio
                        </label>
                        <textarea
                            id="bio"
                            {...register('bio')}
                            className={`shadow appearance-none border rounded-lg w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${errors.bio ? 'border-red-500' : 'border-gray-300'
                                }`}
                            placeholder="Tell us about yourself"
                        />
                        {errors.bio && <p className="text-red-500 text-sm mt-1">{errors.bio.message}</p>}
                    </div>
                    {/* Display error message if there is one */}
                    {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
                    <div className="flex items-center justify-center">
                        <button
                            type="submit"
                            className="bg-teal-600 hover:bg-teal-700 text-white font-bold py-3 px-8 rounded-lg focus:outline-none focus:shadow-outline transition duration-200"

                        >
                            Sign Up
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default SignUpForm;

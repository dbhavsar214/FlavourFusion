import React from 'react'
import Image from 'next/image'
import Link from 'next/link'

const Categories = () => {

    const categoryData = [
        {
            title: 'Vegetarian',
            imageURL: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        },
        {
            title: 'Vegan',
            imageURL: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        },
        {
            title: 'Flexitarian',
            imageURL: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        },
        {
            title: 'Non-vegetarian',
            imageURL: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        },
        {
            title: 'Pescatarian',
            imageURL: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        },
        {
            title: 'Pollotarian',
            imageURL: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1780&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
        },
    ]

    return (
        <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 p-4'>
            {
                categoryData.slice(0, 9).map((category, idx) => (
                    <Link key={idx} href={`/categoryrecipe/${category.title.toLowerCase()}`}>
                        <div className='relative overflow-hidden rounded-lg shadow-lg transform hover:scale-105 transition duration-300 ease-in-out'>
                            <Image className='w-full h-48 object-cover' src={category.imageURL} alt={category.title} height={500} width={500} />
                            <div className='absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center'>
                                <h4 className='text-white text-xl font-semibold'>{category.title}</h4>
                            </div>
                        </div>
                    </Link>
                ))
            }
        </div>
    )
}

export default Categories;

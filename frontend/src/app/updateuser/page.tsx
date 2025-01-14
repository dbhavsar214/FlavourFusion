import React from 'react'
import UpdateUser from '../Components/UpdateUser'

const page = () => {
    const userId= '123';
  return (
    <div>
      <UpdateUser userId={userId} />
    </div>
  )
}

export default page

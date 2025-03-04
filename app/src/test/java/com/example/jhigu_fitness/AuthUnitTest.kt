package com.example.jhigu_fitness

import com.example.jhigu_fitness.repository.UserRepositoryImp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UserRepositoryImpTest {
    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockDatabase: FirebaseDatabase // Add if needed

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    @Mock
    private lateinit var mockVoidTask: Task<Void>

    private lateinit var userRepository: UserRepositoryImp

    @Captor
    private lateinit var authCaptor: ArgumentCaptor<OnCompleteListener<AuthResult>>

    @Captor
    private lateinit var voidCaptor: ArgumentCaptor<OnCompleteListener<Void>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepositoryImp(mockAuth)
    }

    @Test
    fun testLogin_Successful() {
        val email = "test@example.com"
        val password = "testPassword"
        var expectedResult = "Initial Value"

        // Mock successful login
        `when`(mockTask.isSuccessful).thenReturn(true)
        `when`(mockAuth.signInWithEmailAndPassword(any(), any())).thenReturn(mockTask)

        // Define callback
        val callback = { success: Boolean, message: String ->
            expectedResult = message
        }

        // Call the method under test
        userRepository.login(email, password, callback)

        // Verify the task's onCompleteListener is called
        verify(mockTask).addOnCompleteListener(authCaptor.capture())
        // Simulate task completion
        authCaptor.value.onComplete(mockTask)

        // Assert the expected result
        assertEquals("Login successful", expectedResult)
    }

    @Test
    fun testForgetPassword_Successful() {
        val email = "test@example.com"
        var expectedResult = "Initial Value"

        // Mock successful password reset
        `when`(mockVoidTask.isSuccessful).thenReturn(true)
        `when`(mockAuth.sendPasswordResetEmail(email)).thenReturn(mockVoidTask)

        // Define callback
        val callback = { success: Boolean, message: String ->
            expectedResult = message
        }

        // Call the method under test
        userRepository.forgetPassword(email, callback)

        // Verify the task's onCompleteListener is called
        verify(mockVoidTask).addOnCompleteListener(voidCaptor.capture())
        // Simulate task completion
        voidCaptor.value.onComplete(mockVoidTask)

        // Assert the expected result
        assertEquals("Password reset link sent to $email", expectedResult)
    }
}
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RegistrationView from '../views/RegistrationView.vue'
import LoginView from '../views/LoginView.vue'
import ProfileView from '@/views/ProfileView.vue'
import LogoutView from '@/views/LogoutView.vue'
import { useUserStore } from '@/stores/userStore'
import SpecialistSearchView from '@/views/SpecialistSearchView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'About',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: "/registration",
      name: "Registration",
      component: RegistrationView,
      meta: {
        requiresGuest: true
      },
    },
    {
      path: "/login",
      name: "Login",
      component: LoginView,
      meta: {
        requiresGuest: true
      },
    },
    {
      path: "/logout",
      name: "Logout",
      component: LogoutView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/profile",
      name: "Profile",
      component: ProfileView,
      meta: {
        requiresAuth: true
      },
    },
    {
      path: "/search",
      name: "SpecialistSearch",
      component: SpecialistSearchView,
      meta: {
        requiresAuth: true
      }
    }
    
  ]
})

router.beforeEach((to) => {

  const logged = useUserStore().isAuthenticated;
  const authRequired = to.meta["requiresAuth"] || false
  const guestRequired = to.meta["requiresGuest"] || false

  if (!logged && authRequired) {
    return { name: "Login" }
  }
  if (logged && guestRequired) {
    return { name: "Home" }
  }
})

export default router
